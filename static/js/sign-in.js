/* global Vue, axios */
var signInApi = "/api/customers";

const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            customer: new Object(),
            customerFound: new Object()
        };
    },

    mounted() {
        // semicolon separated statements
    },

    methods: {
        // comma separated function declarations
        signIn() {
            signInApi = "/api/customers/" + this.customer.username;
            console.log(signInApi);
            // send GET request
            axios.get(signInApi)
                    .then(response => {
                        // store response in students model
                        this.customerFound = response.data;
                        if(this.customerFound.password == this.customer.password){
                            dataStore.commit("signIn", this.customerFound);
                            window.location = "index.html";
                        }
                        else {
                            alert("Incorrect username or password");
                        }
                    })
                    .catch(error => {
                        console.error(error);
                        alert("Account does not exist");
                    });

        }
    },

    // other modules
    mixins: []

});

// other component imports go here

// import data store
import { dataStore } from './data-store.js'
        app.use(dataStore);


// import navigation  menu component
import { navigationMenu } from './navigation-menu.js';
app.component('navmenu', navigationMenu);

// attach the controller to the <main> tag
app.mount("main");