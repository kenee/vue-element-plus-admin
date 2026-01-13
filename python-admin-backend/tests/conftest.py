import pytest
import requests
from typing import Dict, Any

BASE_URL = "http://localhost:3000"


@pytest.fixture(scope="session")
def base_url():
    return BASE_URL


@pytest.fixture(scope="session")
def api_client():
    class APIClient:
        def __init__(self, base_url: str):
            self.base_url = base_url
            self.token = None
            self.session = requests.Session()

        def request(
            self,
            method: str,
            endpoint: str,
            data: Dict[str, Any] = None,
            params: Dict[str, Any] = None,
            json_data: Dict[str, Any] = None,
            headers: Dict[str, str] = None
        ) -> requests.Response:
            url = f"{self.base_url}{endpoint}"
            request_headers = {}
            if self.token:
                request_headers["Authorization"] = f"Bearer {self.token}"
            if headers:
                request_headers.update(headers)

            response = self.session.request(
                method=method,
                url=url,
                data=data,
                params=params,
                json=json_data,
                headers=request_headers
            )
            return response

        def login(self, username: str, password: str) -> Dict[str, Any]:
            response = self.post("/api/auth/login", json_data={"username": username, "password": password})
            if response.status_code == 200:
                data = response.json()
                if data.get("code") == 0 and "data" in data:
                    self.token = data["data"].get("access_token")
                    return data["data"]
            return {}

        def get(self, endpoint: str, params: Dict[str, Any] = None, headers: Dict[str, str] = None):
            return self.request("GET", endpoint, params=params, headers=headers)

        def post(self, endpoint: str, json_data: Dict[str, Any] = None, data: Dict[str, Any] = None, headers: Dict[str, str] = None):
            return self.request("POST", endpoint, json_data=json_data, data=data, headers=headers)

        def put(self, endpoint: str, json_data: Dict[str, Any] = None, headers: Dict[str, str] = None):
            return self.request("PUT", endpoint, json_data=json_data, headers=headers)

        def patch(self, endpoint: str, json_data: Dict[str, Any] = None, headers: Dict[str, str] = None):
            return self.request("PATCH", endpoint, json_data=json_data, headers=headers)

        def delete(self, endpoint: str, headers: Dict[str, str] = None):
            return self.request("DELETE", endpoint, headers=headers)

    return APIClient(BASE_URL)


@pytest.fixture(scope="function")
def auth_client(api_client):
    api_client.login("admin", "admin")
    return api_client


def assert_api_response(response: requests.Response, expected_code: int = 200, expected_response_code: int = 0):
    """Helper function to assert API response format matches frontend expectations"""
    assert response.status_code == expected_code, f"Expected status {expected_code}, got {response.status_code}"
    data = response.json()
    assert "code" in data, "Response should contain 'code' field"
    assert data["code"] == expected_response_code, f"Expected response code {expected_response_code}, got {data['code']}"
    return data


def check_required_fields(data: Dict[str, Any], required_fields: list, path: str = "root"):
    """Helper function to check if required fields exist in data"""
    for field in required_fields:
        assert field in data, f"Missing required field '{field}' at {path}"
    return True


@pytest.fixture
def assert_response():
    """Fixture to provide assert_api_response function"""
    return assert_api_response


@pytest.fixture
def check_fields():
    """Fixture to provide check_required_fields function"""
    return check_required_fields
