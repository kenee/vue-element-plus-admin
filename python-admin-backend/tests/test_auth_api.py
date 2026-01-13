import pytest


class TestAuthAPI:
    """Test authentication endpoints against frontend expectations"""

    def test_login_success(self, api_client, assert_response, check_fields):
        """Test successful login returns correct format"""
        response = api_client.post(
            "/api/auth/login",
            json_data={"username": "admin", "password": "admin"}
        )

        data = assert_response(response)
        assert "data" in data, "Response should contain 'data' field"

        user_data = data["data"]
        required_fields = ["username", "password", "role", "roleId", "access_token"]
        check_fields(user_data, required_fields, "data")

        assert user_data["username"] == "admin"
        assert user_data["role"] in ["admin", "test"]
        assert "access_token" in user_data

    def test_login_wrong_credentials(self, api_client):
        """Test login with wrong credentials returns error"""
        response = api_client.post(
            "/api/auth/login",
            json_data={"username": "wrong", "password": "wrong"}
        )

        assert response.status_code == 401
        data = response.json()
        assert data["code"] != 0

    def test_logout(self, auth_client, assert_response):
        """Test logout endpoint returns correct format"""
        response = auth_client.get("/api/auth/logout")
        data = assert_response(response)
        assert "data" in data

    def test_login_sets_token(self, api_client):
        """Test login properly sets access token"""
        user_data = api_client.login("admin", "admin")
        assert "access_token" in user_data, "Login should return access_token"
        assert api_client.token is not None, "Client should have token after login"
