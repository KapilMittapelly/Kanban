function clearForm() {
	try {
		$('#addOrUpdateForm').find("input[type=text], textarea").val("");
		$('span.error').each(function (i) { 
			$(this).hide(); 
		});
		$('input.error').each(function (i) { 
			$(this).removeClass('error');
		});
	} catch(e) {
		console.log(e);
	}
}

function onSlideOutOpen(el) {
	try {
    	var elements =  $('[data-operation]');
		
    	if(this.id == 'addItem') {
    		console.log('add clicked');
    		Array.prototype.forEach.call(elements, ele => {
    			//console.log(ele);
    			if(ele.dataset.operation === 'Add') {
    				console.log(ele);
    				$(ele).removeClass('hide');
    			} else {
    				$(ele).addClass('hide');
    				//console.log(ele);
    			}
    		});
    		
    		//Clear form
        	if(clearFlag) {
        		clearForm();
        	} else {
        		clearFlag = true;
        	}
        	
    	} else {
    		var thisId = Number(this.id);
    		console.log('update clicked');
    		Array.prototype.forEach.call(elements, ele => {
    			//console.log(ele);
    			if(ele.dataset.operation === 'Add') {
    				console.log(ele);
    				$(ele).addClass('hide');
    			} else {
    				$(ele).removeClass('hide');
    				//console.log(ele);
    			}
    		});
    		
    		//Clear form
        	if(clearFlag) {
        		clearForm();
        		//Set the form values
        		if(items) {
        			for(let item of items) {
        				console.log(thisId);
        				console.log(item.itemId)
        				if(thisId === item.itemId) {
        					$('#itemId').val(item.itemId);
        					$('#sequenceNum').val(item.sequenceNum);
        					$('#status').val(item.status);
        					$('#itemSummary').val(item.itemSummary);
        					$('#itemDescription').val(item.itemDescription);
        					console.log(item.priority);
        					$('.dropdown-content').children()[item.priority].click();
        					$('#priority').val(item.priority.toString());
        					$('#assignedUser').val(item.assignedUser);
        					$('#jiraNum').val(item.jiraNum);
        					$('#dueDate').val(item.dueDateAsString);
        					$('#updateTms').val(item.updateTms);
        					$('#updateUser').val(item.updateUser);
        					$('label.form-label').each(function (i) { 
        						$(this).addClass('active');
        					});
        					break;
        				}
        			}
        		}
        	} else {
        		clearFlag = true;
        	}
    	}
    	
    	console.log(this);
        console.log(el);
        /* Do Stuff*/
	} catch(e) {
		console.log(e);
	}
}

function slideOut() {
	try {
		if(enableDrag) {
			var instance = $('.button-collapse').sideNav({
		        //menuWidth: window.innerWidth, // Default is 300
		        edge: 'left', // Choose the horizontal origin
		        closeOnClick: false, // Closes side-nav on <a></a> clicks, useful for Angular/Meteor
		        draggable: true, // Choose whether you can drag to open on touch screens,
		        onOpen: onSlideOutOpen // A function to be called when sideNav is opened
		        /*onClose: function(el) {
		            console.log(el);
		        }, // A function to be called when sideNav is closed*/
	       	});
		}		
	} catch(e) {
		console.log(e);
	}
}


$(document).ready(slideOut);