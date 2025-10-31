
window.addEventListener("DOMContentLoaded", function() {
	let viewportWidth = window.innerWidth;
	let viewportHeight = window.innerHeight;
	console.log(`Viewport width is ${viewportWidth}px, height is ${viewportHeight}px`);
	
	start(viewportWidth);
});


function start(width) {
	let imageFrame = document.querySelector('#cropped-image');
	let mainImage = document.createElement("img");
	
	mainImage.src = "../static/images/vecteezy_a-house-with-a-blue-metal-roof-and-red-trim_68737166.jpeg";
	mainImage.className = "hidden-image";
	mainImage.width = Math.floor(width * 1.209);
	mainImage.onload = () => mainImage.classList.add("appeared-image");;
	imageFrame.append(mainImage);
}