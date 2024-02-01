"use strict";

export const navigationMenu = {

	computed: {
		signedIn() {
			return this.customer != null;
		},
		...Vuex.mapState({
			customer: 'customer'
		})
	},

	template:
	`
	<nav>
        <div class= "navbar flex-container flex-row">
                <a v-if="signedIn">Welcome {{customer.firstName}}</a>
		<a href="index.html">Home</a>
		<a href="view-products.html" v-if="signedIn">Browse Products</a>
		<a href="cart.html" v-if="signedIn">View Cart</a>
		<a href="#" v-if="signedIn" @click="signOut()">Sign Out</a>
		<a href="sign-in.html" v-if="!signedIn">Sign In</a>
        </div>
	</nav>
	`,

	methods:{
		signOut() {
			sessionStorage.clear();
			window.location = '.';
		}
	}
};	