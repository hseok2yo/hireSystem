function apiLoginCommonFetch(url, options) {

	options = options || {};
	options.headers = options.headers || {};
	if (!options.headers["X-Requested-With"]) {
		options.headers["X-Requested-With"] = "XMLHttpRequest";
	}

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