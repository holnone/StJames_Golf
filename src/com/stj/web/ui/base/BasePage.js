function keypressTrigger(event) {
	if(window.event && window.event.keyCode == 13) {
		$(event.data.button).trigger("click");
		return false;
	}
}