const maxWidth = window.screen.availWidth;
const maxHeight = window.screen.availHeight;

window.addEventListener("DOMContentLoaded", function() {
	//window.addEventListener("resize", onDimentionsChanged);
	
	start();
});


//not used
function onDimentionsChanged() {
	let viewportWidth = window.innerWidth;
	let viewportHeight = window.innerHeight;
	//document.querySelector("#dev").textContent = `Viewport width is ${viewportWidth}px, height is ${viewportHeight}px`;
	//console.log(`Viewport width is ${viewportWidth}px, height is ${viewportHeight}px`);
	if (window.matchMedia("(height <= 450px)").matches) {
		let info = document.querySelectorAll(".main-info");
		for (let i of info) {
			simplifyTextBlock(i.children[1]);
		}
	}
}

function start() {
	let width = window.innerWidth;
	let imageFrame = document.querySelector('#cropped-image');
	let mainImage = document.createElement("img");
	
	mainImage.src = "../images/254856220_l.jpg";
	mainImage.className = "hidden-image";
	mainImage.width = maxWidth;
	mainImage.onload = function() {
		mainImage.classList.add("appeared-image");
		mainImage.classList.remove("hidden-image");
	}
	imageFrame.append(mainImage);
}


//not used
function simplifyTextBlock(textBlock, lineToLeaveUnmodified = 0) {
	let allLines = textBlock.children;
	for (let i = 0; i < allLines.length; i++) {
		console.log(allLines[i]);
		if (i !== lineToLeaveUnmodified) {
			allLines[i].remove();
		}
	}
}

//not used yet
function textChange(elemWithText, diminish = true) {
	let sizeStyleExp = new RegExp("font-size-(?=(\d+))", "i");
	for (let cl of elemWithText.classList) {
		let result = cl.match(sizeStyleExp)||[];
		if (result.length > 0) {
			elemWithText.classList.remove(cl);
			let prevSizeLevel = Number(result[1])
			if (diminish) {
				if (prevSizeLevel >= 10) return false;
				elemWithText.classList.add(result[0] + String(prevSizeLevel + 1));
			} else {
				if (prevSizeLevel <= 1) return false;
				elemWithText.classList.add(result[0] + String(prevSizeLevel - 1));
			}
			return true;
		}
	}
	return false;
}