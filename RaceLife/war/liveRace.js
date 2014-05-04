function getUrlParameter(urlParameterName) {
	if (urlParameterName = (new RegExp('[?&]'
			+ encodeURIComponent(urlParameterName) + '=([^&]*)'))
			.exec(location.search))
		return decodeURIComponent(urlParameterName[1]);
}

var locationField = document.getElementById("locationField");
var timeCounter = 0;
var raceStartLatitude = 21.300307699999998;
var raceStartLongitude = -157.85089019999998;

function showPosition(position) {

	var raceDistance = calculateRaceDistance(raceStartLatitude,
			raceStartLongitude, position.coords.latitude,
			position.coords.longitude);

	var savePositionUrl = "savePosition" + "?RaceName="
			+ encodeURIComponent(getUrlParameter("RaceName"))
			+ "&RacerNickName="
			+ encodeURIComponent(getUrlParameter("RacerNickName"))
			+ "&Timestamp=" + encodeURIComponent(position.timestamp)
			+ "&Latitude=" + encodeURIComponent(position.coords.latitude)
			+ "&Longitude=" + encodeURIComponent(position.coords.longitude)
			+ "&RaceDistance=" + raceDistance;

	$.post(savePositionUrl, {}, function(data, status) {

		var racersList = jQuery.parseJSON(data);

		var item = "";
		var racersCount = 0;

		$.each(racersList, function(key, val) {

			// item += "<li class=' ui-btn-up-e '></b><small>" +
			// val.racerNickName
			// + " " + val.timestamp + " " + val.raceDistance
			// + "</small></li> ";

			racersCount++;

			if (racersCount < 7) {
				item += "<div align=center valign=middle class='racer" + racersCount + "-6_'>"
						+ val.racerNickName + "  </div>"
						+ " <div class='racerPos" + racersCount + "-6'>"
						+ val.raceDistance + " meters</div>";
			}
		});

		racersField.innerHTML = item;

		/*
		 * racersField.innerHTML = "Race:" + getUrlParameter("RaceName") + "<br>Racer:" +
		 * getUrlParameter("RacerNickName") + "<br>Counter: " + timeCounter + "<br>Timestamp: " +
		 * position.timestamp + "<br>Latitude: " + position.coords.latitude + "<br>Longitude: " +
		 * position.coords.longitude + "<br>Altitude: " +
		 * position.coords.altitude + "<br>Heading: " +
		 * position.coords.heading + "<br>Speeding: " + position.coords.speed + "<br>Accuracy: " +
		 * position.coords.accuracy + "<br><br>Race Positions:" + item;
		 */

	});
}

var intervalCounter = setInterval(function() {
	timeCounter++;
	if (navigator.geolocation) {
		navigator.geolocation.watchPosition(showPosition);
	}

	if (timeCounter == 10) {
		clearInterval(intervalCounter);
	}
}, 3000);

function stopTimerFunction() {
	clearInterval(intervalCounter);
}

function calculateRaceDistance(lat1, lon1, lat2, lon2) {

	var radlat1 = Math.PI * lat1 / 180;
	var radlat2 = Math.PI * lat2 / 180;
	var radlon1 = Math.PI * lon1 / 180;
	var radlon2 = Math.PI * lon2 / 180;
	var theta = lon1 - lon2;
	var radtheta = Math.PI * theta / 180;
	var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1)
			* Math.cos(radlat2) * Math.cos(radtheta);
	dist = Math.acos(dist);
	dist = dist * 180 / Math.PI;
	dist = dist * 60 * 1.1515;

	// (unit == "Meters") {
	dist = dist * 1609.344;

	return precise_round(dist,6);
}

function precise_round(num,decimals) {
    return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
}