# Flask Testing Service

## Features
- Add, list, and delete datasets.
- Run operations (sum, max, min) on datasets.
- Includes unit, performance and integration tests.


## Setup
1. Install dependencies:
   ```bash
   pip install -r requirements.txt
   
## How to run?

1. ```bash 
   python app.py
2. ```bash 
   python -m unittest discover -s tests -p "test_service.py"
3. ```bash 
   pytest tests/test_integration.py
4. ```bash
    locust -f test_performance.py --host=http://localhost:8089
 
