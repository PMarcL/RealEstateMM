$(document).ready(function () {
  getActiveSellers()
})

function addActiveBuyerStatToDOM (activeUsers) {
  var htmlToAppend = "<li class ='stats-list-item'> Active buyers in the last 6 months: " + activeUsers.numberOfActiveBuyer + '</li>'
  $('.stats-list').append(htmlToAppend)
}

function addActiveSellerStatToDOM (activeUsers) {
  var htmlToAppend = "<li class ='stats-list-item'> Number of sellers with at least one on sale property: " + activeUsers.numberOfActiveSeller + '</li>'
  $('.stats-list').append(htmlToAppend)
}

function getActiveSellers () {
  $.ajax({
    url: 'http://localhost:8080/stats/numberofactiveuser',
    type: 'GET',
    contentType: 'application/json',

    success: function (activeUsers) {
      addActiveBuyerStatToDOM(activeUsers)
      addActiveSellerStatToDOM(activeUsers)
    }
  })
}
