document.addEventListener('DOMContentLoaded', function () {

    var docElem = document.documentElement;
    var kanban = document.querySelector('.kanban-section');
    var itemContainers = Array.prototype.slice.call(kanban.querySelectorAll('.board-column-content'));
    var muuriInstances = [];
    var dragCounter = 0;

    itemContainers.forEach(function (container) {

        var muuri = new Muuri(container, {
                items: '.board-item',
                dragEnabled: true,
                dragSortGroup: 'column',
                dragSortWith: 'column',
                dragContainer: document.body
            })
            .on('dragStart', function (item) {
                ++dragCounter;
                docElem.classList.add('dragging');
                item.getElement().style.width = item.getWidth() + 'px';
                item.getElement().style.height = item.getHeight() + 'px';
            })
            .on('dragEnd', function (item) {
                console.log('dragEnd');
                console.log(item);
                if (--dragCounter < 1) {
                    docElem.classList.remove('dragging');
                }
            })
            .on('dragReleaseEnd', function (item) {
                console.log('dragReleaseEnd');
                console.log(item);
                item.getElement().style.width = '';
                item.getElement().style.height = '';
                muuriInstances.forEach(function (muuri) {
                    muuri.refreshItems();
                });
            });

        muuriInstances.push(muuri);

    });

});
