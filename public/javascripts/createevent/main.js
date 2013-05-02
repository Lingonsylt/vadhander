console.log("createvent.js");

/**
 * Find the users position
 *
 * Required html:
 * <any-element class="findposition"
 *              data-longitude-input-selector="<selector>"
 *              data-longitude-input-selector="<selector>"
 */
var geosearch = geosearch || {};
geosearch.findposition = {
    init : function() {
        geosearch.findposition.baseNode = $('.findposition');
        geosearch.findposition.infoMessageNode = geosearch.findposition.baseNode.find(geosearch.findposition.baseNode.attr("data-info-message-selector"));
        geosearch.findposition.infoErrorNode = geosearch.findposition.baseNode.find(geosearch.findposition.baseNode.attr("data-error-message-selector"));
        geosearch.findposition.latitudeInputNode = geosearch.findposition.baseNode.find(geosearch.findposition.baseNode.attr("data-latitude-input-selector"));
        geosearch.findposition.longitudeInputNode = geosearch.findposition.baseNode.find(geosearch.findposition.baseNode.attr("data-longitude-input-selector"));
        geosearch.findposition.longitudeInputNode = geosearch.findposition.baseNode.find(geosearch.findposition.baseNode.attr("data-longitude-input-selector"));

        // Test if the user has geolocation support, if not hide the button
        if (!!navigator.geolocation) {
            geosearch.findposition._search();
        } else {
            geosearch.findposition.baseNode.hide();
        }
    },

    _search : function () {
        // Show a spinner
        $(this).addClass('searching');
        // Try to find the position
        navigator.geolocation.getCurrentPosition(
            geosearch.findposition._positionFound,
            geosearch.findposition._positionError
        );
    },

    _positionFound : function (position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        geosearch.findposition.latitudeInputNode[0].value(latitude);
        geosearch.findposition.longitudeInputNode[0].value(longitude);
        geosearch.findposition.baseNode.removeClass('searching');
        geosearch.findposition.infoErrorNode.html(
            '<span class="label label-success">Position hittad</span> Din position är: ' + longitude + ', ' + latitude
        );
    },

    _positionError : function (err) {
        geosearch.findposition.baseNode.removeClass('searching');
        if (err.code == 1) {
            // The user said no to our request for their location
            geosearch.findposition._showError('Du måste dela din position för att vi ska kunna hitta var du är.');
        } else if (err.clode == 2) {
            geosearch.findposition._showError('Din position är inte tillgänglig för tillfället.');
        }
        else {
            geosearch.findposition._showError('Kunde inte hitta din position (felkod ' + err.code + ')');
        }
    },

    _showError : function (message) {
        message = '<span class="label label-important">Position ej hittad</span> ' +
            message + ' (' + '<a class="retry">Försök igen</a>)';

        geosearch.findposition.infoErrorNode.html(message);
        geosearch.findposition.infoErrorNode.find(".retry").click(function () {
            geosearch.findposition._search();
        })
    }
};


geosearch.findposition.init();