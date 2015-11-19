$(document).ready(function () {
  getActiveUsers()
  getSoldProperties()
  getOnSaleProperties()

})

function getOnSaleProperties () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofonsaleproperties',
    type: 'GET',
    contentType: 'application/json',

    success: function (properties) {
      for (var propertyType in properties) {
        var htmlToAppend = "<li class ='stats-list-item'>" + propertyType.charAt(0).toUpperCase() + propertyType.slice(1)
          + 's for sale: ' + properties[propertyType] + '</li>'
        $('.stats-list').append(htmlToAppend)
      }
    }
  })
}

function getSoldProperties () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofsoldproperties',
    type: 'GET',
    contentType: 'application/json',

    success: function (properties) {
      var htmlToAppend = "<li class ='stats-list-item'> Properties sold this year: " + properties.numberOfPropertiesSoldThisYearResponse + '</li>'
      $('.stats-list').append(htmlToAppend)

    }
  })
}

function getActiveUsers () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofactiveuser',
    type: 'GET',
    contentType: 'application/json',

    success: function (activeUsers) {
      var htmlToAppend = "<li class ='stats-list-item'> Active Buyers: " + activeUsers.numberOfActiveBuyer + '</li>'
        + "<li class ='stats-list-item'> Active Sellers: " + activeUsers.numberOfActiveSeller + '</li>'

      $('.stats-list').append(htmlToAppend)
    }
  })
}
