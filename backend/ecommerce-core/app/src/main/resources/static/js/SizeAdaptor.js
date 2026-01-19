let allSizes = {
	windowSizes: [[1900, 950], [1800, 900], [1700, 850], [1600, 800], [1500, 750], [1400, 700], [1300, 650], [1200, 600], [1100, 550], [1000, 500], [900, 450], [800, 400], [700, 350], [600, 300], [500, 250]],
}

Object.defineProperty(allSizes, "windowSizes", { enumerable: false, writable: false });

class smoothSizeAdaptor {
	constructor(fontS, iconS, logoS) {
		this.fontSize = fontS;
		this.iconSize = iconS;
		this.logoSize = logoS;
	
		this.textChangePace = {
			changePace: 1,
			value: 1.5,
			prev: 0,
		}

		this.iconChangePace = {
			changePace: 3,
			value: 0.1,
			prev: 0,
		}

		this.logoChangePace = {
			changePace: 2,
			value: 0.1,
			prev: 0,
		}
	}
	
	adjustMainFont(current) {
		if (current % this.textChangePace.changePace === 0 || Math.abs(current - this.textChangePace.prev) >= this.textChangePace.changePace) {
			let html = document.querySelector("html");
			console.log(this.fontSize);
			this.fontSize += this.textChangePace.value * Math.trunc((this.textChangePace.prev - current) / this.textChangePace.changePace);
			html.style.cssText = `font-size: ${this.fontSize}px;`;
			this.textChangePace.prev = current;
		}
	}
	
	adjustIcons(current) {
		if (current % this.iconChangePace.changePace === 0 || Math.abs(current - this.iconChangePace.prev) >= this.iconChangePace.changePace) {
			let icons = document.querySelectorAll(".icon");
			this.iconSize += this.iconChangePace.value * Math.trunc((this.iconChangePace.prev - current) / this.iconChangePace.changePace);
			for (let icon of icons) {
				icon.style.cssText = `transform: scale(${this.iconSize}, ${this.iconSize});`;
			}
			this.iconChangePace.prev = current;
		}
	}
	
	adjustLogo(current) {
		if (current % this.logoChangePace.changePace === 0 || Math.abs(current - this.logoChangePace.prev) >= this.logoChangePace.changePace) {
			let logos = document.querySelectorAll(".logo");
			this.logoSize += this.logoChangePace.value * Math.trunc((this.logoChangePace.prev - current) / this.logoChangePace.changePace);
			console.log("prev - current: ", this.logoChangePace.prev - current);
			for (let logo of logos) {
				logo.style.cssText = `transform: scale(${this.logoSize}, ${this.logoSize});`;
				console.log("logo size: ", this.logoSize);
			}
			this.logoChangePace.prev = current;
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
				var2: [0, 24],
				var3: [0, 23, 28],
			},
			
			label: {
				var2: [0, 28],
				var3: [0, 23, 211],
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
		document.querySelector("#dev").textContent = `Viewport width is ${viewportWidth}px, height is ${viewportHeight}px`;
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