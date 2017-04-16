import jose from "node-jose";
import got from "got";


let validationResult = null;


export class User {
    constructor() {
        this.loggedIn = false;
        this.idToken = null;
    }
}


export let getUser = () => {
    let user = window.sessionStorage.getItem("user");
    if (user === null) {
        user = JSON.stringify(new User());
        window.sessionStorage.setItem("user", user);
    }
    return JSON.parse(user);
};

export let validateIdToken = async (idToken) => {
    let keys = (await got("https://www.googleapis.com/oauth2/v3/certs", {json: true})).body;

    // let opts = {
    //     handlers: {
    //         "exp": {
    //             complete: function (jws) {
    //                 console.log("########" + JSON.stringify(jws));
    //                 jws.header.exp = new Date(jws.header.exp);
    //             }
    //         }
    //     }
    // };

    let keystore = jose.JWK.createKeyStore();

    await jose.JWK.asKeyStore(keys).then(function (result) {
        keystore = result;
    });


    await jose.JWS.createVerify(keystore).verify(idToken)
        .then(function (result) {
            validationResult = result;
        });
};


export let recordAuthenticationSuccess = async (idToken, component) => {
    let userObj = getUser();
    let idTokenToBeValidated = (idToken === null) ? userObj.idToken : idToken;

    if (idTokenToBeValidated !== null) {

        await validateIdToken(idToken);

        if (validationResult !== null) {
            let userObj = getUser();
            userObj.loggedIn = true;
            userObj.idToken = idToken;
            window.sessionStorage.setItem("user", JSON.stringify(userObj));
        }

    }
    return component;
};




