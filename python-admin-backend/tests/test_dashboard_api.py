import pytest


class TestWorkplaceAPI:
    """Test workplace dashboard endpoints against frontend expectations"""

    def test_workplace_total(self, auth_client, assert_response, check_fields):
        """Test workplace total statistics endpoint"""
        response = auth_client.get("/api/workplace/total")
        data = assert_response(response)
        assert "data" in data

        total_data = data["data"]
        required_fields = ["project", "access", "todo"]
        check_fields(total_data, required_fields, "data")

    def test_workplace_project(self, auth_client, assert_response):
        """Test workplace projects endpoint"""
        response = auth_client.get("/api/workplace/project")
        data = assert_response(response)
        assert "data" in data

        projects = data["data"]
        assert isinstance(projects, list), "Projects should be a list"

    def test_workplace_dynamic(self, auth_client, assert_response):
        """Test workplace dynamic endpoint"""
        response = auth_client.get("/api/workplace/dynamic")
        data = assert_response(response)
        assert "data" in data

        dynamics = data["data"]
        assert isinstance(dynamics, list), "Dynamics should be a list"

    def test_workplace_team(self, auth_client, assert_response):
        """Test workplace team endpoint"""
        response = auth_client.get("/api/workplace/team")
        data = assert_response(response)
        assert "data" in data

        team = data["data"]
        assert isinstance(team, list), "Team should be a list"

    def test_workplace_radar(self, auth_client, assert_response):
        """Test workplace radar chart endpoint"""
        response = auth_client.get("/api/workplace/radar")
        data = assert_response(response)
        assert "data" in data

        radar = data["data"]
        assert isinstance(radar, list), "Radar data should be a list"


class TestAnalysisAPI:
    """Test analysis dashboard endpoints against frontend expectations"""

    def test_analysis_total(self, auth_client, assert_response, check_fields):
        """Test analysis total statistics endpoint"""
        response = auth_client.get("/api/analysis/total")
        data = assert_response(response)
        assert "data" in data

        total_data = data["data"]
        required_fields = ["users", "messages", "moneys", "shoppings"]
        check_fields(total_data, required_fields, "data")

    def test_analysis_user_access_source(self, auth_client, assert_response):
        """Test analysis user access source endpoint"""
        response = auth_client.get("/api/analysis/userAccessSource")
        data = assert_response(response)
        assert "data" in data

        sources = data["data"]
        assert isinstance(sources, list), "User access sources should be a list"

    def test_analysis_weekly_user_activity(self, auth_client, assert_response):
        """Test analysis weekly user activity endpoint"""
        response = auth_client.get("/api/analysis/weeklyUserActivity")
        data = assert_response(response)
        assert "data" in data

        activity = data["data"]
        assert isinstance(activity, list), "Weekly activity should be a list"

    def test_analysis_monthly_sales(self, auth_client, assert_response):
        """Test analysis monthly sales endpoint"""
        response = auth_client.get("/api/analysis/monthlySales")
        data = assert_response(response)
        assert "data" in data

        sales = data["data"]
        assert isinstance(sales, list), "Monthly sales should be a list"
