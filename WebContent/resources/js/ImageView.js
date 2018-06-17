var i

function renderImageFile() {  
	
		file = this.files[0];
		var imgReader = new FileReader();
		imgReader.onload = function () {
			var show = document.createElement("img");
			var imageURL = event.target.result;
			show.name="img";
			show.id="imgView"
			show.src=imageURL;
			var imageView=document.getElementById("showimage");
			while(imageView.childNodes.length >=1){
				imageView.removeChild(imageView.firstChild);				
			}
			imageView.appendChild(show);
			   
		};

		imgReader.readAsDataURL(file);
}


function init(){

	var files = document.getElementsByName("photo");

	 	files[0].addEventListener("change",renderImageFile,false);
}

window.addEventListener("load",init,false);
	