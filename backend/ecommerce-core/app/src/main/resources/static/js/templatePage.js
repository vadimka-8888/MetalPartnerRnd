window.addEventListener("DOMContentLoaded", function() {
	let navigation = document.querySelector("#navigation");
	navigation.addEventListener("click", loadPage);
});

function loadPage(event) {
	let targetTab = event.target;
	targetTab.classList.toggle("active");
	targetTab.disabled = true;
	let number = targetTab.id;
	
	let nav = event.currentTarget;
	let prev_number = nav.getAttribute("data-current-active");
	let prev_active_tab = nav.querySelector("#" + prev_number);
	prev_active_tab.classList.toggle("active");
	prev_active_tab.disabled = false;
	
	nav.setAttribute("data-current-active", number);
}
