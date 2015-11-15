/**
 * Created by MiiKE on 14.11.2015.
 */
angular.module('starter.controllers')
.controller('NeedsCtrl',function($scope, $state, Need, $ionicModal, Tag, Fulfillment, $filter) {



  $scope.callbackMethod = function (query) {

    var tags = Tag.query({
      q: query
    }).$promise.then(function(result) {
      var tags = result;
      if (tags.length === 0) {
        tags.push(query);
      }

      return tags;
    });

    return tags;
  }

  $ionicModal.fromTemplateUrl('templates/new-need.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.createModal = modal;
  });

  $ionicModal.fromTemplateUrl('templates/need-detail.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.detailModal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeCreate = function() {
    $scope.createModal.hide();
  };

  // Open the login modal
  $scope.create = function() {
    $scope.createModal.show();
  };
  $scope.need = {};
  // Perform the login action when the user submits the login form
  $scope.doCreate = function(need) {
    //console.log('Doing login', $scope.loginData);

    need.user = {username:window.localStorage['user']};
    need.location = {name:'Wien',location:[0,0]};
    Need.post(need, function() {
      $scope.closeCreate();
    });
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system

  };

  $scope.closeDetail = function() {
    $scope.detailModal.hide();
  }

  $scope.show = function(need) {
    Need.get({id:need.id},function(need){
      $scope.detailNeed = need;
      $scope.notFulfilled = $scope.detailNeed.amount > ($scope.detailNeed.amount - $scope.detailNeed.amountDone);
      $scope.notMyNeed = window.localStorage.user !== $scope.detailNeed.user.username;
      $scope.detailModal.show();
    });//angular.copy(need);

  };

  $scope.fulfillment = new Fulfillment();
  $scope.doFulfillment = function() {
    $scope.fulfillment.user = window.localStorage.user;
    $scope.fulfillment.requestId = $scope.detailNeed.id;
    $scope.fulfillment.until = $filter('date')($scope.fulfillment.untilDate, 'yyyy-MM-dd');
    Fulfillment.save($scope.fulfillment, function() {
      $state.go('app.needs.todo');
      $scope.fulfillment = new Fulfillment();
    });
  };

  $scope.editNeed = function(need) {
    $scope.need = angular.copy(need);
    $scope.need.startDate = new Date(Date.parse($scope.need.startDate));
    $scope.need.endDate = new Date(Date.parse($scope.need.endDate));
    $scope.newNeed(need);
  };

  $scope.deleteNeed = function(need) {
    var need = angular.copy(need);
    //need.user = window.localStorage.user;
    Need.delete(need, function() {
      $scope.reload();
    });
  };

  $scope.doneFulfillment = function(fulfillment) {
    var tmp = {};
    tmp.user = window.localStorage.user;
    tmp.id = fulfillment.id;
    tmp.done = true;
    Fulfillment.save(tmp, function() {
      $scope.detailNeed = Need.get({id: $scope.detailNeed.id, user: window.localStorage.user});
    });
  };

})
  .controller('AllCtrl',function($scope,Need) {
    $scope.showCreate = false;
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'all'});
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  }).controller('ByMeCtrl',function($scope,Need) {
    $scope.showCreate = true;
  $scope.reload = function() {
    $scope.needs = Need.query({'filter': 'user', 'user': window.localStorage['user']});
  }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  })
  .controller('ForMeCtrl',function($scope,Need) {
    $scope.showCreate = false;
    $scope.reload = function() {
      $scope.needs = Need.query({'filter': 'subscriptions', 'user': window.localStorage['user']});
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  })
  .controller('ToDoCtrl',function($scope, Fulfillment) {
    $scope.showCreate = false;
    $scope.reload = function() {
      $scope.needs = Fulfillment.query({user: window.localStorage.user});
    }
    $scope.$on('$ionicView.beforeEnter', function() {
      $scope.reload();
    });
  });
