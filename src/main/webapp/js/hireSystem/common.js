function apiLoginCommonFetch(url, options) {

	return fetch(url, options)
		.then(function(response) {

			if (response.status === 401) {

				var currentUrl =
					window.location.pathname + window.location.search;

				location.href =
					"/hireSystem/login/login.do?redirectUrl="
					+ encodeURIComponent(currentUrl);

				return null;		
			}

			return response.json();

		});
}