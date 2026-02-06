"use strict";

window.addEventListener("DOMContentLoaded", function() {
	//let navigation = document.querySelector("#navigation");
	//navigation.addEventListener("click", loadPage);
	let sizeAdaptor = new UIAdaptor(new replaceAdaptor(), new smoothSizeAdaptor(40, 0.8, [1.5, 1.1, 1.1]));
	sizeAdaptor.resizeElementsOnStart();
	window.addEventListener("resize", () => sizeAdaptor.resizeElements());
});

// function loadPage(event) {
// 	let targetTab = event.target;
// 	targetTab.classList.toggle("active");
// 	targetTab.disabled = true;
// 	let number = targetTab.id;
	
// 	let nav = event.currentTarget;
// 	let prev_number = nav.getAttribute("data-current-active");
// 	let prev_active_tab = nav.querySelector("#" + prev_number);
// 	prev_active_tab.classList.toggle("active");
// 	prev_active_tab.disabled = false;
	
// 	nav.setAttribute("data-current-active", number);
// }

let allSizes = {
	windowSizes: [[1900, 950], [1800, 900], [1700, 850], [1600, 800], [1500, 750], [1400, 700], [1300, 650], [1200, 600], [1100, 550], [1000, 500], [900, 450], [800, 400], [700, 350], [600, 300], [500, 250]],
}

Object.defineProperty(allSizes, "windowSizes", { enumerable: false, writable: false });

class smoothSizeAdaptor {
	constructor(fontS, iconS, logoS) {

		this.textChange = {
			startSize: fontS,
			changePace: 1,
			value: 1.5,
			prev: 0,
		}

		this.iconChange = {
			startSize: iconS,
			changePace: 3,
			value: 0.1,
			prev: 0,
		}

		this.logoChange = {
			//starting logo-1, header logo-2, footer logo-3
			startSize: logoS,
			changePace: [2, 4, 4],
			value: [0.15, 0.1, 0.1],
			prev: [0, 0, 0],
		}

		this.logoTypes = {
			BIG: "logo-1",
			STATIC: "logo-2",
			ORDINARY: "logo-3",
		}
	}

	onStart() {
		for (let i = 0; i < this.logoSize.length; i++) {
			let logos = document.querySelectorAll(`.logo-${i + 1}`);
		}
	}

	adjustMainFont(current) {
		if (current % this.textChange.changePace === 0 || Math.abs(current - this.textChange.prev) >= this.textChange.changePace) {
			let html = document.querySelector("html");
			console.log("current font size:" + this.textChange.startSize);
			this.textChange.startSize += this.textChange.value * Math.trunc((this.textChange.prev - current) / this.textChange.changePace);
			html.style.cssText = `font-size: ${this.textChange.startSize}px;`;
			this.textChange.prev = current;
		}
	}
	
	adjustIcons(current) {
		if (current % this.iconChange.changePace === 0 || Math.abs(current - this.iconChange.prev) >= this.iconChange.changePace) {
			let icons = document.querySelectorAll(".icon");
			this.iconChange.startSize += this.iconChange.value * Math.trunc((this.iconChange.prev - current) / this.iconChange.changePace);
			for (let icon of icons) {
				icon.style.cssText = `transform: scale(${this.iconChange.startSize}, ${this.iconChange.startSize});`;
			}
			this.iconChange.prev = current;
		}
	}
	
	adjustLogo(current) {
		console.log("trying to resize logo");
		
		if (current % this.logoChange.changePace === 0 || Math.abs(current - this.logoChange.prev) >= this.logoChange.changePace) {
			
			let logos = document.querySelectorAll(`.logo`);
			this.logoSize[i] += this.logoChange.value * Math.trunc((this.logoChange.prev - current) / this.logoChange.changePace);
			console.log("prev - current: ", this.logoChange.prev - current);
			for (let logo of logos) {
				//let type = Number(logo.className.match(RegExp(`logo-(?<typeNum>\\d{1})`, "i")).groups.typeNum) - 1;
				logo.style.cssText = `transform: scale(${this.logoChange.startSize}, ${this.logoChange.startSize});`;
				console.log("logo size: ", this.logoChange.startSize);
			}
			this.logoChange.prev = current;
		}
	}
	
	defaultAdjustment() {
		this.adjustMainFont(0);
		this.adjustIcons(0);
		this.adjustLogo(0);
	}
}

class replaceAdaptor {
	constructor() {
		this.replacementOrder = {
			button: {
				var2: [0, 6],
				var3: [0, 6, 10],
			},
			
			label: {
				var2: [0, 6],
				var3: [0, 6, 12],
			},
		}
	}
	
	replaceWithShorthands(currentIdx) {
		for (let elementType of Object.keys(this.replacementOrder)) {
			for (let category of Object.keys(this.replacementOrder[elementType])) {
				let multivariantElements = document.querySelectorAll(`.${elementType}.multivariant.${category}`);
				if (multivariantElements !== null && multivariantElements.length != 0) {
					let changeArray = this.replacementOrder[elementType][category];
					let i = 0;
					while (true) {
						if (changeArray[i] >= currentIdx) {
							if (changeArray[i] == currentIdx) {
								break;
							} else {
								i -= 1;
								break;
							}
						}
						if (i < changeArray.length - 1) {
							i += 1;
						} else {
							break;
						}
					}
					//console.log(`${elementType}-${category}: ${i}, current size index: ${this.currentIndex}`);
					
					
					for (let element of multivariantElements) {
						let currentVariantIdx = Number(element.getAttribute("data-variant-idx"));
						if (currentVariantIdx != i) {
							let allVariants = Array.from(element.children);
							allVariants[currentVariantIdx].hidden = true;
							allVariants[i].hidden = false;
							element.setAttribute("data-variant-idx", String(i));
						}
					}
				}
			}
		}
	}
	
	defaultReplacement() {
		this.replaceWithShorthands(0);
	}
}

class UIAdaptor {
	constructor(rAdaptor, ssAdaptor) {
		this.currentIndex = -1;
		this.allSizes = allSizes;
		
		this.replaceAdaptor = rAdaptor;
		this.sizeAdaptor = ssAdaptor;
	}
	
	
	resizeElements(event) {
		let viewportWidth = window.innerWidth;
		let viewportHeight = window.innerHeight;
		//document.querySelector("#dev").textContent = `Viewport width is ${viewportWidth}px, height is ${viewportHeight}px`;
		for (let i = 0; i < this.allSizes.windowSizes.length; i++) {
			if (viewportWidth >= this.allSizes.windowSizes[i][0] && viewportHeight >= this.allSizes.windowSizes[i][1]) {
				if (i !== this.currentIndex) {
					this.currentIndex = i;
					
					this.replaceAdaptor.replaceWithShorthands(this.currentIndex);
					
					this.sizeAdaptor.adjustMainFont(this.currentIndex);
					this.sizeAdaptor.adjustIcons(this.currentIndex);
					this.sizeAdaptor.adjustLogo(this.currentIndex);
				}
				break;
			}
		}
	}
	
	resizeElementsOnStart(event) {
		this.replaceAdaptor.defaultReplacement();
		this.sizeAdaptor.defaultAdjustment();
		this.resizeElements(event);
	}
	
	
	
	
	
	
	
	//is not used at all
	adjust() {
		for (let elementType of Object.keys(this.allSizes)) {
			let elementFeature = this.allSizes[elementType]["feature"];
			for (let category of Object.keys(this.allSizes[elementType])) {
				let elementSizes = this.allSizes[elementType][category];
				let newSize = elementSizes[this.currentIndex];
				let oldSize = this.prevIndex >= 0 ? elementSizes[this.prevIndex] : -1;
				if (newSize !== oldSize) {
					let elements = document.querySelectorAll(`.${elementType}.${category}`);
					if (elements !== null) {
						for (let e of elements) {
							e.classList.remove(e.className.match(RegExp(`${elementFeature}-\\d+`, "i")));
							e.classList.add(`${elementFeature}-${newSize}`);
						}
					}
				} else {
					let elements = document.querySelectorAll(`.${elementType}.${category}.multivariant`);
					if (elements !== null) {
						for (let e of elements) {
							e.classList.remove(e.className.match(RegExp(`${elementFeature}-\\d+`, "i")));
							e.classList.add(`${elementFeature}-${newSize}`);
						}
					}
				}
			}
		}
	}
}


