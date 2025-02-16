import csv
import os
import numpy as np
import json
import joblib
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.ensemble import RandomForestClassifier
from flask import Flask, request, jsonify, render_template_string
import random
import pandas as pd
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score, roc_auc_score, cohen_kappa_score, confusion_matrix, roc_curve 

# Rander the plots without GUI
import matplotlib
matplotlib.use('Agg')

from matplotlib import pyplot as plt
import seaborn as sns

CSV_FILE = "test.csv"

accuracy = 1 
precision = 1
recall = 1
f1 = 1
auc = 1 
kappa = 1

df_company = pd.DataFrame()
importance_mean = 0
performance_mean = 0

test_dataset = pd.read_csv(CSV_FILE)

rf = RandomForestClassifier()
rf = joblib.load('../notebook/rf.joblib')
le = LabelEncoder()
le = joblib.load('../notebook/le.joblib')
sc = StandardScaler()
sc = joblib.load('../notebook/scaler.joblib')

app = Flask(__name__)


@app.route('/survey')
def survey():
    return render_template_string(open("survey.html").read())

# Ensure the CSV file exists with headers
if not os.path.exists(CSV_FILE):
    with open(CSV_FILE, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerow([
            "id", "Gender", "Customer Type", "Age", "Type Of Travel", "Class",
            "Flight Distance", 'Inflight wifi service', 'Departure/Arrival time convenient',
            'Ease of Online booking', 'Gate location', 'Food and drink', 'Online boarding',
            'Seat comfort', 'Inflight entertainment', 'On-board service', 'Leg room service',
            'Baggage handling', 'Checkin service', 'Inflight service', 'Cleanliness'
        ])

@app.route('/submit-survey', methods=['POST'])
def submit_survey():
    data = request.form.to_dict()

    # Add generated fields
    data["customer_type"] = random.choice(["Loyal Customer", "Disloyal Customer"])
    data["flight_distance"] = random.randint(500, 2500)
    data["departure_delay"] = random.randint(0, 60)
    data["arrival_delay"] = random.randint(0, 60)

    print(data)

    # Prepare row data for the CSV
    row = [
        0,
        data.get("id"),
        data.get("gender"),
        data["customer_type"],
        data.get("age"),
        data.get("travelType"),
        data.get("class"),
        data["flight_distance"],
        data.get("wifi"),
        data.get("timeConvenient"),
        data.get("booking"),
        data.get("gateLocation"),
        data.get("foodDrink"),
        data.get("onlineBoarding"),
        data.get("seatComfort"),
        data.get("entertainment"),
        data.get("onboard"),
        data.get("legroom"),
        data.get("baggage"),
        data.get("checkin"),
        data.get("inflight"),
        data.get("Cleanliness"),
        data["departure_delay"],
        data["arrival_delay"],
        ''
    ]
    
    row[-1] = "neutral or dissatisfied" if sum(map(int, row[8:-3])) < 60 else "satisfied"

    # Append the data to the CSV
    try:
        with open(CSV_FILE, mode="a", newline="") as file:
            writer = csv.writer(file)
            writer.writerow(row)
        return jsonify({"message": "Survey submitted and saved successfully!"}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route('/results')
def results():
    return render_template_string(open("results.html").read())
@app.route('/get-results', methods=['GET'])
def get_results():

    model()

    global df_company
    global accuracy
    global precision
    global recall
    global f1
    global auc 
    global kappa
    global importance_mean 
    global performance_mean

    # Sample numeric results and image paths for the demo (you can replace with actual logic)
    results = {
      "accuracy": accuracy,
      "precision": precision,
      "recall": recall,
      "f1": f1,
      "auc": auc,
      "kappa": kappa,
    }

    # Sample image paths
    images = {
        "test1": "static/images/confmatrix.png",
        "test2": "static/images/roccurve.png",
        "ipa":"static/images/ipa.png",
    }

    ipa = df_company.to_json(orient="records")

    means = {
        "importance_mean": importance_mean,
        "performance_mean": performance_mean,
    }

    # Return the data in JSON format to be handled by JavaScript
    return jsonify({"results": results, "ipa": ipa, "means": means, "images": images})


@app.route('/model')
def model():
  
  global accuracy
  global precision
  global recall
  global f1
  global auc 
  global kappa
  

  test = test_dataset.copy()
  test = test.drop(columns=['Unnamed: 0', 'id'])
  test['satisfaction'] = le.transform(test['satisfaction'])
  categorical = test.select_dtypes(include=['object']).columns
  test = pd.get_dummies(test, columns=categorical, drop_first=True)
  test.dropna(axis=0,inplace=True)
  numeric_columns = test.select_dtypes(include=['float64', 'int64']).columns.to_list()
  numeric_columns.remove('satisfaction')
  test[numeric_columns] = sc.transform(test[numeric_columns])
  y_test = test["satisfaction"]
  test = test.drop(columns=['satisfaction'])
  y_pred = rf.predict(test)
  y_proba = rf.predict_proba(test)[:, 1]
  accuracy = accuracy_score(y_test, y_pred)
  precision = precision_score(y_test, y_pred)
  recall = recall_score(y_test, y_pred)
  f1 = f1_score(y_test, y_pred)
  auc = roc_auc_score(y_test, y_proba)
  kappa = cohen_kappa_score(y_test, y_pred)
  
  # Plot confusion matrix
  conf_matrix = confusion_matrix(y_test, y_pred)

  plt.ioff()

  plt.figure(figsize=(6, 6))
  sns.heatmap(conf_matrix, annot=True, fmt="d", cmap="Blues", xticklabels=['Neutral/Dissatisfied', 'Satisfied'], yticklabels=['Neutral/Dissatisfied', 'Satisfied'])
  plt.title('Confusion Matrix', fontsize=16)
  plt.xlabel('Predicted', fontsize=14)
  plt.ylabel('Actual', fontsize=14)
  plt.savefig("static/images/confmatrix.png")

  # # Draw ROC curve
  fpr, tpr, _ = roc_curve(y_pred, y_proba)
  plt.figure(figsize=(8, 6))
  plt.plot(fpr, tpr, label=f"AUC = {auc:.2f}", color="darkorange")
  plt.plot([0, 1], [0, 1], linestyle="--", color="gray")
  plt.title('ROC Curve', fontsize=16)
  plt.xlabel('False Positive Rate', fontsize=14)
  plt.ylabel('True Positive Rate', fontsize=14)
  plt.legend(loc="lower right")
  plt.savefig("static/images/roccurve.png")



  # IPA Analysis

  # Extract the related columns for IPA analysis
  columns_of_interest = test_dataset.iloc[:, 8:-3]
  # Get insterested column names
  attributes = columns_of_interest.columns.tolist()
  # Calculate the mean for each of the columns
  means = columns_of_interest.mean().round(2)

  # Open and read the JSON file
  with open('../config/ipa.json', 'r') as file:
    data = json.load(file)
    importance_list = data["company_2"]

  # Set the data_set for IPA analysis
  data_set = {
    "Attribute":attributes,
    "Importance": importance_list,
    "Performance": means.tolist(),
  }

  global df_company
  # Create data frame object
  df_company = pd.DataFrame(data_set)

  global importance_mean
  global performance_mean
  # Calculate mean importance and performance
  importance_mean = df_company['Importance'].mean().round(2)
  performance_mean = df_company['Performance'].mean().round(2)

  # Scatter plot of Importance vs. Performance
  plt.figure(figsize=(8,8))
  plt.scatter(df_company['Performance'], df_company['Importance'], c='blue', s=100)
    
  # Add text labels for each attribute
  for i, row in df_company.iterrows():
    plt.text(row['Performance'] + 0.1, row['Importance'] + 0.1, row['Attribute'], fontsize=10)
    
  # Add gridlines based on mean values
  plt.axhline(y=importance_mean, color='red', linestyle='--', label="Mean Importance")
  plt.axvline(x=performance_mean, color='red', linestyle='--', label="Mean Performance")
    
  # Add labels and title
  plt.xlabel("Performance")
  plt.ylabel("Importance")
  plt.title("Importance-Performance Analysis (IPA)")
  plt.legend()
  plt.grid()
  plt.savefig("static/images/ipa.png")

  df_company['Quadrant'] = df_company.apply(ipa_classify, axis=1)


def ipa_classify(row):
  global importance_mean
  global performance_mean

  if row['Importance'] > importance_mean and row['Performance'] < performance_mean:
      return "Concentrate Here"
  elif row['Importance'] > importance_mean and row['Performance'] > performance_mean:
      return "Good Work"
  elif row['Importance'] < importance_mean and row['Performance'] < performance_mean:
      return "Low Priority"
  else:
      return "Possible Overkill"