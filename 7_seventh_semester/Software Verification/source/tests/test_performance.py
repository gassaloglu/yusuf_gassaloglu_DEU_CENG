from locust import HttpUser, task, between
import json
import random


class TestUser(HttpUser):
    wait_time = between(1, 5)  # Simulate user waiting time between requests
    
    @task
    def add_dataset(self):
        """Test the /add-dataset endpoint."""
        dataset_name = f"test_dataset_{random.randint(1, 10000)}"
        data = [random.randint(1, 100) for _ in range(50)]
        payload = {"dataset_name": dataset_name, "data": data}

        with self.client.post("/add-dataset", json=payload, catch_response=True) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure("Failed to add dataset")
                
# Define the load test settings:
# Number of users (simulated clients), spawn rate (how quickly they are spawned), and the host URL.
if __name__ == "__main__":
    # Running Locust from the command line:
    # locust -f performance_test.py --host=http://localhost:5000
    pass
