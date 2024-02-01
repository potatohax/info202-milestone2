/* global Vue, axios */
var registerApi = "/api/register";

const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            customer: new Object()
        };
    },

    mounted() {
        // semicolon separated statements
    },

    methods: {
        // comma separated function declarations
        createAccount() {
            // send GET request
            console.log(this.customer);
            axios.post(registerApi, this.customer)
                    .then(() => {
                        window.location = "index.html";
                    })
                    .catch(error => {
                        console.error(error);
                        alert("An error occurred - check the console for details.");
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