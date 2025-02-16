import unittest
from app import app, sample_data


class TestService(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        """Set up the Flask test client and initial test state."""
        cls.client = app.test_client()
        cls.client.testing = True

    def test_health_check(self):
        """Test the health check endpoint."""
        response = self.client.get("/")
        self.assertEqual(response.status_code, 200)
        self.assertIn("Enhanced Testing Service is Running", response.get_json()["message"])

    def test_add_dataset(self):
        """Test adding a dataset."""
        payload = {"dataset_name": "unit_test_dataset", "data": [5, 15, 25]}
        response = self.client.post("/add-dataset", json=payload)
        self.assertEqual(response.status_code, 201)
        self.assertIn("unit_test_dataset", sample_data)

    def test_run_test_sum(self):
        """Test running the sum operation on a dataset."""
        sample_data["unit_test_dataset"] = [5, 15, 25]
        payload = {"dataset_name": "unit_test_dataset", "operation": "sum"}
        response = self.client.post("/run-test", json=payload)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.get_json()["result"], 45)

    def test_list_datasets(self):
        """Test listing datasets."""
        # Add dataset via the /add-dataset endpoint
        payload = {"dataset_name": "unit_test_dataset", "data": [5, 15, 25]}
        self.client.post("/add-dataset", json=payload)

        # Now call /list-datasets and verify
        response = self.client.get("/list-datasets")
        self.assertEqual(response.status_code, 200)
        self.assertIn("unit_test_dataset", response.get_json()["datasets"])

    def test_delete_dataset(self):
        """Test deleting a dataset."""
        # Add dataset to ensure it exists
        payload = {"dataset_name": "unit_test_dataset", "data": [5, 15, 25]}
        self.client.post("/add-dataset", json=payload)

        # Delete dataset and verify
        response = self.client.delete("/delete-dataset/unit_test_dataset")
        self.assertEqual(response.status_code, 200)
        self.assertNotIn("unit_test_dataset", sample_data)


if __name__ == "__main__":
    unittest.main()
