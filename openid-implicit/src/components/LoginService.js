import * as idTokenVerifier from "./IdTokenVerifier";

export class User {
    constructor() {
        this.loggedIn = false;
        this.idToken = null;
        this.validatedPayload = null;
        // this.email = null;
        // this.locale = null;
        // this.picture = null;
        // this.userId = null;
        // this.name = null;
        // this.exp = null;
    }
}

export let getUser = () => {
    let user = window.sessionStorage.getItem("user");
    if (user === null) {
        user = JSON.stringify(new User());
        window.sessionStorage.setItem("user", user);
    } else {
        let userObj = JSON.parse(user);
        if(userObj.validatedPayload !== null && !idTokenVerifier.isStillValid(userObj.validatedPayload.exp)) {
            user = JSON.stringify(new User());
            window.sessionStorage.setItem("user", user);
        }
    }
    return JSON.parse(user);
};


async function populateUserState(idTokenToBeValidated, userObj) {

    if (idTokenToBeValidated !== null) {

        await idTokenVerifier.verify(idTokenToBeValidated);

        if (idTokenVerifier.validationResult !== null) {

            userObj.idToken = idTokenToBeValidated;
            userObj.validatedPayload = idTokenVerifier.validationResult;
            userObj.loggedIn = true;
            // userObj.email = validatedPayload.email;
            // userObj.locale = validatedPayload.locale;
            // userObj.picture = validatedPayload.picture;
            // userObj.userId = validatedPayload.userId;
            // userObj.name = validatedPayload.name;
            // userObj.exp = validatedPayload.exp;
            // userObj.loggedIn = (idTokenVerifier.isStillValid(validatedPayload.exp));

            window.sessionStorage.setItem("user", JSON.stringify(userObj));
        }

    } else {
        userObj.loggedIn = false;
        userObj.idToken = null;
        userObj.validatedPayload = null;
        // userObj.email = null;
        // userObj.locale = null;
        // userObj.picture = null;
        // userObj.userId = null;
        // userObj.name = null;
        // userObj.exp = null;
    }
}

export let recordAuthenticationSuccess = async (idToken, component) => {
    let userObj = getUser();

    let idTokenToBeValidated = (idToken === null) ? userObj.idToken : idToken;

    await populateUserState(idTokenToBeValidated, userObj);

    return component;
};




