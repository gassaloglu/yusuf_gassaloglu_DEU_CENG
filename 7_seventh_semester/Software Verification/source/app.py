from flask import Flask, request, jsonify

app = Flask(__name__)

# In-memory storage for datasets
sample_data = {}

@app.route("/")
def health_check():
    """Health check endpoint."""
    return jsonify({"message": "Enhanced Testing Service is Running"}), 200

@app.route("/add-dataset", methods=["POST"])
def add_dataset():
    """Add a new dataset."""
    data = request.json
    dataset_name = data.get("dataset_name")
    dataset = data.get("data")

    if not dataset_name or not isinstance(dataset, list):
        return jsonify({"error": "Invalid input"}), 400

    sample_data[dataset_name] = dataset
    return jsonify({"message": f"Dataset '{dataset_name}' added successfully"}), 201

@app.route("/list-datasets", methods=["GET"])
def list_datasets():
    """List all datasets."""
    return jsonify({"datasets": list(sample_data.keys())}), 200

@app.route("/run-test", methods=["POST"])
def run_test():
    """Run a test on a dataset."""
    data = request.json
    dataset_name = data.get("dataset_name")
    operation = data.get("operation")

    if dataset_name not in sample_data:
        return jsonify({"error": f"Dataset '{dataset_name}' not found"}), 404

    dataset = sample_data[dataset_name]

    if operation == "sum":
        result = sum(dataset)
    elif operation == "max":
        result = max(dataset)
    elif operation == "min":
        result = min(dataset)
    else:
        return jsonify({"error": "Invalid operation"}), 400

    return jsonify({"dataset": dataset_name, "operation": operation, "result": result}), 200

@app.route("/delete-dataset/<dataset_name>", methods=["DELETE"])
def delete_dataset(dataset_name):
    """Delete a dataset."""
    if dataset_name in sample_data:
        del sample_data[dataset_name]
        return jsonify({"message": f"Dataset '{dataset_name}' deleted successfully"}), 200
    return jsonify({"error": f"Dataset '{dataset_name}' not found"}), 404

if __name__ == "__main__":
    app.run(debug=True)
