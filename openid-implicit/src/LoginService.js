import * as idTokenVerifier from "./IdTokenVerifier";

export class User {
    constructor() {
        this.idToken = null;
        this.validatedPayload = null;
    }

    get loggedIn() {
        return !!(this.validatedPayload !== null && idTokenVerifier.isStillValid(this.validatedPayload.exp));
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


async function populateUserState(idTokenToBeValidated, userObj) {

    if (idTokenToBeValidated !== null) {

        await idTokenVerifier.verify(idTokenToBeValidated);

        if (idTokenVerifier.validationResult !== null) {

            userObj.loggedIn = true;
            userObj.idToken = idTokenToBeValidated;
            userObj.validatedPayload = idTokenVerifier.validationResult.payload;
            window.sessionStorage.setItem("user", JSON.stringify(userObj));
        }

    } else {
        userObj.loggedIn = false;
        userObj.idToken = null;
        userObj.validatedPayload = null;
    }
}

export let recordAuthenticationSuccess = async (idToken, component) => {
    let userObj = getUser();

    let idTokenToBeValidated = (idToken === null) ? userObj.idToken : idToken;

    await populateUserState(idTokenToBeValidated, userObj);

    return component;
};




