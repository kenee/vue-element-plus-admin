import pytest


class TestMenuAPI:
    """Test menu endpoints against frontend expectations"""

    def test_get_menu_list(self, auth_client, assert_response, check_fields):
        """Test getting menu list returns correct format"""
        response = auth_client.get("/api/menu")

        data = assert_response(response)
        assert "data" in data, "Response should contain 'data' field"

        result = data["data"]
        required_fields = ["list"]
        check_fields(result, required_fields, "data")

        if isinstance(result["list"], list) and len(result["list"]) > 0:
            menu_item = result["list"][0]
            menu_fields = ["path", "name", "meta"]
            check_fields(menu_item, menu_fields, "data.list[0]")

    def test_menu_item_structure(self, auth_client, assert_response):
        """Test menu item has required structure"""
        response = auth_client.get("/api/menu")
        data = assert_response(response)

        if "data" in data and isinstance(data["data"]["list"], list):
            for item in data["data"]["list"]:
                assert "path" in item, f"Menu item missing 'path': {item}"
                assert "name" in item, f"Menu item missing 'name': {item}"
                assert "meta" in item, f"Menu item missing 'meta': {item}"
                assert "title" in item["meta"], f"Menu meta missing 'title': {item.get('meta')}"

                if "children" in item:
                    for child in item["children"]:
                        assert "path" in child, f"Child menu missing 'path': {child}"
                        assert "name" in child, f"Child menu missing 'name': {child}"
