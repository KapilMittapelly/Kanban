var columnGrids = [];
var boardGrid;

function onDragStart(item) {
	
	try {
		// Let's set fixed widht/height to the dragged item
	    // so that it does not stretch unwillingly when
	    // it's appended to the document body for the
	    // duration of the drag.
	    item.getElement().style.width = item.getWidth() + 'px';
	    item.getElement().style.height = item.getHeight() + 'px';
	} catch(e) {
		console.log(e);
	}
}

function onDragReleaseEnd(item) {
	
	try {
		//print to which column the item has dragged
		var elem = item.getElement();
		console.log(elem.id);
		console.log(elem.getAttribute('data-item'));
		console.log(elem.parentElement.getAttribute('data-column'))
		
	    // Let's remove the fixed width/height from the
	    // dragged item now that it is back in a grid
	    // column and can freely adjust to it's
	    // surroundings.
	    item.getElement().style.width = '';
	    item.getElement().style.height = '';
	    // Just in case, let's refresh the dimensions of all items
	    // in case dragging the item caused some other items to
	    // be different size.
	    columnGrids.forEach(function (grid) {
	        grid.refreshItems();
	    });
	    
	    //make ajax call
	    if(elem.getAttribute('data-item') != elem.parentElement.getAttribute('data-column')) {
	    	udpateItem(elem);
	    } else {
	    	console.log('No update required');
	    }		
	} catch(e) {
		console.log(e);
	}
}

function onLayoutStart() {
    // Let's keep the board grid up to date with the
    // dimensions changes of column grids.
	try {
		boardGrid.refreshItems().layout();
	} catch(e) {
		console.log(e);
	}
}

function udpateItem (elem) {
	try {
		var data = {};
        var item_data = elem.id.split('_');
        var status;
        switch(elem.parentElement.getAttribute('data-column')) {
			case 'backlog':
				status = 1;
				break;
			case 'todo':
				status = 2;
				break;
			case 'wip':
				status = 3;
				break;
			case 'done':
				status = 4;
				break;
        };
        data["boardId"] = item_data[0];
        data["itemId"] = item_data[1];
        data["sequenceNum"] = item_data[2];
        data["status"] = status;
        
        $.ajax({
             type: "POST",
             contentType: "application/json",
             url: "/updateItem",
             data: JSON.stringify(data),
             dataType: 'json',
             timeout: 600000,
             success: function (data) {
                 elem.setAttribute('data-item', elem.parentElement.getAttribute('data-column'));
                 console.log('success');
             },
             error: function (e) {
                 console.log('failure');
                 //$.get( "/kanban", { boardId: item_data[0], userId: '5' } );
                 location.reload(true);
             }
        });
		
	} catch(e) {
		console.log(e);
	}
}

function instantiateItems(container) {
    
	try {
		// Instantiate column grid.
	    var grid = new Muuri(container, {
	            items: '.board-item',
	            layoutDuration: 400,
	            layoutEasing: 'ease',
	            dragEnabled: enableDrag ? enableDrag : false,
	            dragSort: function () {
	                return columnGrids;
	            },
	            dragSortInterval: 0,
	            dragContainer: document.body,
	            dragReleaseDuration: 400,
	            dragReleaseEasing: 'ease'
	        })
	    	.on('send', function (data) {
	    		return false;
	    	})
	        .on('dragStart', onDragStart)
	        .on('dragReleaseEnd', onDragReleaseEnd)
	        .on('layoutStart', onLayoutStart);

	    // Add the column grid reference to the column grids
	    // array, so we can access it later on.
	    columnGrids.push(grid);
	} catch(e) {
		console.log(e);
	}
}

function buildKanban () {

	try {
		var itemContainers = [].slice.call(document.querySelectorAll('.board-column-content'));
	    
	    // Define the column grids so we can drag those
	    // items around.
	    itemContainers.forEach(instantiateItems);

	    // Instantiate the board grid so we can drag those
	    // columns around.
	    boardGrid = new Muuri('.board', {
	        layoutDuration: 400,
	        layoutEasing: 'ease',
	        dragEnabled: false,
	        dragSortInterval: 0,
	        dragStartPredicate: {
	            handle: '.board-column-header'
	        },
	        dragReleaseDuration: 400,
	        dragReleaseEasing: 'ease'
	    });
	} catch(e) {
		console.log(e);
	}
}

function submitGetRequest(checkbox) {
	try {
		const boardId = $('#boardId').val();
        const userId = $('#updateUser').val();
		if($(this).is(':checked')) {
	        const requestUrl = '/kanban?boardId=' + boardId + '&userId=' + userId + '&assignedToMe=true';
	        window.location.href = requestUrl;
	    } else {
	        const requestUrl = '/kanban?boardId=' + boardId + '&userId=' + userId + '&assignedToMe=false';
	        window.location.href = requestUrl;
	    }
	} catch(e) {
		console.log(e);
	}
}

function callOnLoadFunctions() {
	try {
		buildKanban();
		//
		$('input[name=assignedToMe]').change(submitGetRequest);
	} catch(e) {
		console.log(e);
	}
}
document.addEventListener('DOMContentLoaded', callOnLoadFunctions);