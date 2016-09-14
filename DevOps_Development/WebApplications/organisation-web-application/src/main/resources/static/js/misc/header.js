var NAVIGATION_LINK_MAP =
{
	HOME: "#menu_home",
	ABOUT: "#menu_about",
	EMPLOYEE: "#menu_manage",
	PROJECT: "#menu_manage",
	DEVICE_REGISTRATION: "#menu_manage",
	PROJECT_ASSIGNMENT: "#menu_manage"
};

function showActiveMenu(linkName) {
	for( var key in NAVIGATION_LINK_MAP) {
		$(NAVIGATION_LINK_MAP[key]).removeClass("active");
	}
	$(linkName).addClass("active");
}