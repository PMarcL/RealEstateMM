$(document).ready(function () {
  getNumberOfSellersWithAProperty()
  getActiveSellers()
})

function getNumberOfSellersWithAProperty () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofactiveseller',
    type: 'GET',
    contentType: 'application/json',

    success: function (numberOfSeller) {
      var htmlToAppend = "<li class ='stats-list-item'> Number of sellers with at least one on sale property: " + numberOfSeller.numberOfSellerWithAProperty + '</li>'
      $('.stats-list').append(htmlToAppend)

    }
  })
}

function getActiveSellers () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofactiveusers',
    type: 'GET',
    contentType: 'application/json',

    success: function (activeUsers) {
      var htmlToAppend = "<li class ='stats-list-item'> Active buyers in the last 6 months: " + activeUsers.numberOfActiveSeller + '</li>'

      $('.stats-list').append(htmlToAppend)
    }
  })
}
