import pytest


class TestRoleAPI:
    """Test role endpoints against frontend expectations"""

    def test_get_role_list(self, auth_client, assert_response, check_fields):
        """Test getting role list returns correct format"""
        response = auth_client.get("/api/role")

        data = assert_response(response)
        assert "data" in data, "Response should contain 'data' field"

        result = data["data"]
        required_fields = ["list", "total"]
        check_fields(result, required_fields, "data")

    def test_role_list_content(self, auth_client, assert_response, check_fields):
        """Test role list contains valid role data"""
        response = auth_client.get("/api/role")
        data = assert_response(response)

        if isinstance(data["data"]["list"], list) and len(data["data"]["list"]) > 0:
            role = data["data"]["list"][0]
            role_fields = ["roleName", "role"]
            check_fields(role, role_fields, "data.list[0]")
