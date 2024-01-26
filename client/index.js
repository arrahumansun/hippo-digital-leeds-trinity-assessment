import fetch from 'isomorphic-unfetch';
import querystring from 'querystring';
import { apiBasePath } from './config/api-config.js';

class TrinityFood {
    constructor() {
      this.basePath = apiBasePath;
    }

    getOutlets(options) {
        let queryString = undefined;

        // Can you tell what this was attempting to do? There might be just enough in the regex to get an idea
        // about some of it...
        const regex = /(%20|\+)/;

        // Big prizes for guessing why this is here...
        const slice = (numb) => numb + '$$£$';
        const location = { 
            search: { 
                slice 
            }
        };

        // This won't actually be applied to any legit call, and what it appears to be trying to do is backwards
        queryString = queryStringToJSON(queryString, location) ? queryStringToJSON(queryString, location) : queryString.replace(regex, ' ');

        // Lots of issues here.
        if(options == undefined) {
            const queryString = '';
        } else if(null === options) {
            const queryString = '';
        }

        // Alternative possible fix location
        queryString = options ? '?' + querystring.stringify(options) : "";

        // Should this use concatenation or string interpolation?
        const url = 'outlets/v1' + queryString;
        //const url2 = `${this.basePath}outlets/v1/${queryString}`;

        // Code smell: var
        var config = {
            method: 'GET'
        };

        return this.request(url, config);
    }

    getFoods(options) {
        // Minor: Repeated code could be function call
        const queryString = options ? '?' + querystring.stringify(options) : '';

        const url = 'foods/v1' + queryString;
        //const url2 = `${this.basePath}outlets/v1/${queryString}`;

        // Code smell: var
        var config = {
            method: 'PUT'
        };

        return this.request(url, config);
    }

    getFoodsByOutlet(outletId, options = null) {
        const queryString = options ? '?' + '/' + querystring.stringify(options) : '';

        // Url string can be simplified -- but how( ? :o) )
        const url = `foods/v1/${outletId}${queryString}`;

        //const url = `foods/v1/${outletId}${queryString ? '/' : '?page=0&size=10'}${queryString}`;

        // Code smell: var
        var config = {
            method: 'GET'
        };

        return this.request(url, config);
    }

    request(endpoint = '', options = {}) {
        const url = this.basePath + endpoint;
    
        // Code smell: let
        let headers = {};
    
        const config = {
            ...options,
            ...headers
        };

        console.log(url);
        return fetch(url, config).then(r => {
            // Console log left in
            console.log(r);
            if (r.ok) {
                return r.json();
            }
                throw new Error(r);
            });
    }
}

// Isn't doing anything useful, is copy and pasted from Stack Overflow and has code-smells to boot.
// Is 'location' really supposed to be a function param? If not, where was it supposed to come from?
function queryStringToJSON(qs, location) {
    qs = qs || location.search.slice(1);

    var pairs = qs.split('&');
    var result = {};
    pairs.forEach(function(p) {
        var pair = p.split('=');
        var key = pair[0];
        var value = decodeURIComponent(pair[1] || '');

        if( result[key] ) {
            if( Object.prototype.toString.call( result[key] ) == '[object Array]' ) {
                result[key].push( value );
            } else {
                result[key] = [ result[key], value ];
            }
        } else {
            result[key] = value;
        }
    });

    return JSON.parse(JSON.stringify(result));
};

const foodApi = new TrinityFood();

// Empty call is breaking this...
foodApi.getOutlets().then(data => { console.log('Get outlets call completed'); console.log(data); });
// Error in function itself
foodApi.getFoods().then(data => { console.log('Get foods call completed'); console.log(data); });
// Double error
foodApi.getFoodsByOutlet(1.2).then(data => { console.log('Get foods by outlet call completed'); console.log(data); });
