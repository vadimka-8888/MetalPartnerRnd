
window.addEventListener("DOMContentLoaded", function() {
	let nav = document.querySelector(".admin-navigation");
	console.log("ok");
	nav.addEventListener("click", moveToPage);
	
	let tabs = document.querySelectorAll(".admin-article-tab");
	for (let tab of tabs) {
		tab.addEventListener("mouseover", illuminateTab);
		tab.addEventListener("mouseout", obscureTab);
		tab.addEventListener("click", expandTab);
	}
	
	//loadAllArticles();
});

function moveToPage(event) {
	let button = event.target;
	let currentActiveButton = this.getAttribute("data-active-page");
	if (currentActiveButton === button.id) return;
	button.classList.add("admin-nav-button-selected");
	let prev_button = this.querySelector("#" + currentActiveButton);
	prev_button.classList.remove("admin-nav-button-selected");
	this.setAttribute("data-active-page", button.id);
}

function illuminateTab(event) {
	this.querySelector("path").classList.add("tab-triangle-illuminated");
}

function obscureTab(event) {
	this.querySelector("path").classList.remove("tab-triangle-illuminated");
}

function expandTab(event) {
	let article = this.parentElement;
	if (article != null) {
		let p = article.querySelector(".admin-article-content");
		p.classList.toggle("admin-article-content");
		p.classList.toggle("admin-article-content-unfolded");
		
		let triangle = this.querySelector("path");
		triangle.classList.add("tab-triangle-illuminated");
		triangle.setAttribute("transform", "rotate(180)");
		this.removeEventListener("mouseout", obscureTab);
		
		this.removeEventListener("click", expandTab);
		this.addEventListener("click", shrinkTab);
	}
}

function shrinkTab(event) {
	let article = this.parentElement;
	if (article != null) {
		let p = article.querySelector(".admin-article-content-unfolded");
		p.classList.toggle("admin-article-content-unfolded");
		p.classList.toggle("admin-article-content");
		
		let triangle = this.querySelector("path");
		triangle.classList.remove("tab-triangle-illuminated");
		triangle.setAttribute("transform", "");
		this.addEventListener("mouseout", obscureTab);
		
		this.removeEventListener("click", shrinkTab);
		this.addEventListener("click", expandTab);
	}
}
function loadAllArticles() { //Not working without a server
	//let container = document.querySelector(".admin-articles-container");
	let receivedDataPromise = fetch("article.html").catch(error => alert(error)).then(function(value) {
		if(value.ok) {
			alert(receivedDataPromise.status);
			//container.innerHTML = 
		}
	});
}