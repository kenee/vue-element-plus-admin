import pytest


class TestUserAPI:
    """Test user endpoints against frontend expectations"""

    def test_get_user_list(self, auth_client, assert_response, check_fields):
        """Test getting user list returns correct format"""
        response = auth_client.get("/api/user")

        data = assert_response(response)
        assert "data" in data, "Response should contain 'data' field"

        result = data["data"]
        required_fields = ["list", "total"]
        check_fields(result, required_fields, "data")

        if isinstance(result["list"], list) and len(result["list"]) > 0:
            user = result["list"][0]
            user_fields = ["username", "password", "role", "roleId"]
            check_fields(user, user_fields, "data.list[0]")

    def test_get_user_list_with_pagination(self, auth_client, assert_response):
        """Test user list pagination parameters work"""
        response = auth_client.get("/api/user", params={"pageIndex": 1, "pageSize": 10})
        data = assert_response(response)
        assert "data" in data
        assert "list" in data["data"]
        assert "total" in data["data"]

    def test_get_user_list_with_filter(self, auth_client, assert_response):
        """Test user list filtering works"""
        response = auth_client.get("/api/user", params={"username": "admin"})
        data = assert_response(response)
        assert "data" in data
        assert "list" in data["data"]
