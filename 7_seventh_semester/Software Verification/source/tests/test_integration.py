import pytest
import requests

BASE_URL = "http://127.0.0.1:5000"

def test_health_check():
    """Test the health check endpoint."""
    response = requests.get(BASE_URL + "/")
    assert response.status_code == 200
    assert response.json()["message"] == "Enhanced Testing Service is Running"

def test_add_dataset():
    """Test adding a new dataset."""
    payload = {"dataset_name": "test_dataset", "data": [10, 20, 30]}
    response = requests.post(BASE_URL + "/add-dataset", json=payload)
    assert response.status_code == 201
    assert "Dataset 'test_dataset' added successfully" in response.json()["message"]

def test_list_datasets():
    """Test listing datasets."""
    response = requests.get(BASE_URL + "/list-datasets")
    assert response.status_code == 200
    assert "test_dataset" in response.json()["datasets"]

def test_run_test_sum():
    """Test running a sum operation on a dataset."""
    payload = {"dataset_name": "test_dataset", "operation": "sum"}
    response = requests.post(BASE_URL + "/run-test", json=payload)
    assert response.status_code == 200
    assert response.json()["result"] == 60

def test_delete_dataset():
    """Test deleting a dataset."""
    response = requests.delete(BASE_URL + "/delete-dataset/test_dataset")
    assert response.status_code == 200
    assert "Dataset 'test_dataset' deleted successfully" in response.json()["message"]
