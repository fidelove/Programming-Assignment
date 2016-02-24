viabillApp.controller('viabillCtrl', function($scope, $http, $mdDialog, $mdConstant) {

	$scope.allCompanies = [];
	$scope.getCompanyDetails = null;
	$scope.updateCompany = null;
	$scope.addBenefitialOwners = [];
	$scope.escapeKeys = [$mdConstant.KEY_CODE.ENTER, $mdConstant.KEY_CODE.COMMA];
	$http.defaults.useXDomain = true;

	var updateCompanyDetailsOldName = null;

	$scope.createNewCompany = function() {

		$scope.allCompanies = [];

		var newCompany = {	'name' : $scope.createCompany.name,
							'address' : $scope.createCompany.address,
							'city' : $scope.createCompany.city,
							'country' : $scope.createCompany.country,
							'email' : $scope.createCompany.email,
							'phoneNumber' : $scope.createCompany.phoneNumber,
							'beneficiaOwner' : [$scope.createCompany.beneficiaOwner.trim(',')]
							};
		$http.post(
			'http://localhost:9090/createCompany',
			newCompany
		).then(function(response) {
			var result = response.data;

			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('New company created succesfully')
				.textContent('The new company ' + $scope.createCompany.name + ' with ID: ' + result.idCompany + ' has been succesfully created')
				.ok('Thanks')
			);
		}, function(response) {
			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('Error ' + response.status )
				.textContent('Error when creating the new company into the system : ' + response.data.error + '. Please try again later')
				.ok('No problem')
			);
		});
    };

    $scope.retrieveAllCompanies = function() {

		if($scope.allCompanies.length == 0){

			$http.get(
				'http://localhost:9090/getCompanies'
  			).then(function(response) {
				$scope.allCompanies = response.data;
  			}, function(response) {
				$mdDialog.show(
					$mdDialog.alert()
					.parent(angular.element(document.querySelector('#appContainer')))
					.clickOutsideToClose(true)
					.title('Error ' + response.status )
					.textContent('Error when retrieving data from server : ' + response.data.error + '. Please try again later')
					.ok('No problem')
				);
			});
		}
    }

	$scope.retrieveCompanyDetails = function() {
		retrieveCompany($scope.selectedCompanyDetails, function(response){
			$scope.getCompanyDetails = response;
		});
    }

	$scope.retrieveCompanyUpdate = function() {
		if($scope.selectedCompanyUpdate != null) {
			retrieveCompany($scope.selectedCompanyUpdate, function(response){
				$scope.updateCompany = response;
				updateCompanyDetailsOldName = response.name;
			});
		}
    }

	retrieveCompany = function(idCompany, callback) {
		$http.get(
			'http://localhost:9090/getCompany/' + idCompany
		).then(function(response) {
			callback(response.data)
		}, function(response) {
			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('Error ' + response.status )
				.textContent('Error when retrieving data from server : ' + response.data.error + '. Please try again later')
				.ok('No problem')
			);
		});
    }

	$scope.updateExistingCompany = function() {

		var updateCompanyWithoutBeneficiaOwner = angular.copy($scope.updateCompany);
		updateCompanyWithoutBeneficiaOwner.beneficiaOwner = null;
	
		$http.put(
			'http://localhost:9090/updateCompany/' + $scope.selectedCompanyUpdate,
			updateCompanyWithoutBeneficiaOwner
		).then(function(response) {
			var result = response.data;

			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('New company created succesfully')
				.textContent('The company ' + $scope.updateCompany.name + ' with ID: ' + $scope.selectedCompanyUpdate + ' has been succesfully updated')
				.ok('Thanks')
			);

			if(updateCompanyDetailsOldName != $scope.updateCompany.name) {
				$scope.allCompanies = [];
				$scope.retrieveAllCompanies();
			}

			if($scope.getCompanyDetails != null && updateCompanyDetailsOldName != $scope.getCompanyDetails.name) {
				$scope.getCompanyDetails = null;
			}
		}, function(response) {
			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('Error ' + response.status )
				.textContent('Error when updating the company ' + $scope.updateCompany.name + ' with ID: ' + $scope.selectedCompanyUpdate + ' into the system : ' + response.data.error + '. Please try again later')
				.ok('No problem')
			);
		});

	}

	$scope.addOwnersToCompany = function() {
		$http.put(
			'http://localhost:9090/addOwner/' + $scope.selectedCompanyAddBenefitial,
			$scope.addBenefitialOwners
		).then(function(response) {
			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('New benefitial owner(s) added succesfully')
				.textContent('The benefitial owner(s) ' + $scope.addBenefitialOwners.toString() + ' has been succesfully added to company ID : ' + $scope.selectedCompanyAddBenefitial)
				.ok('Thanks')
			);

			if($scope.selectedCompanyDetails == $scope.selectedCompanyAddBenefitial) {
				$scope.getCompanyDetails = null;
			}
			
			if($scope.selectedCompanyUpdate == $scope.selectedCompanyAddBenefitial) {
				$scope.updateCompany = null;
			}
			
		}, function(response) {
			$mdDialog.show(
				$mdDialog.alert()
				.parent(angular.element(document.querySelector('#appContainer')))
				.clickOutsideToClose(true)
				.title('Error ' + response.status )
				.textContent('Error when trying to add benefitial owner(s) to a company  : ' + response.data.error + '. Please try again later')
				.ok('No problem')
			);
		});
    }

	$scope.cleanAddOwnersToCompany = function() {
		$scope.addBenefitialOwners = [];
		$scope.companyAddBenefitial = null;
    }
});
