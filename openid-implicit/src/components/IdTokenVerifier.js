import jose from "node-jose";
import got from "got";


export let validationResult = null;

export function isStillValid(tokenExpiry) {
    const currentTimeRoundedToSeconds = (new Date().getTime() / 1000);
    return currentTimeRoundedToSeconds < tokenExpiry;
}

export let verify = async (idToken) => {
    let keys = (await got("https://www.googleapis.com/oauth2/v3/certs", {json: true})).body;

    let keystore = jose.JWK.createKeyStore();

    await jose.JWK.asKeyStore(keys).then(function (result) {
        keystore = result;
    });


    function validateExpire(tokenExpiry) {
        if (!isStillValid(tokenExpiry)) {
            validationResult = null;
        }
    }

    function validateNonce(nonce) {
        if ("n-0S6_WzA2Mj" !== nonce) {
            validationResult = null;
        }
    }

    function validateIssuer(issuer) {
        if ("https://accounts.google.com" !== issuer) {
            validationResult = null;
        }
    }

    function validateClient(client) {
        if ("262806823822-va4iok5mm0f0qhh44j217t6ni4adrpqh.apps.googleusercontent.com" !== client) {
            validationResult = null;
        }
    }

    await jose.JWS.createVerify(keystore).verify(idToken)
        .then(function (result) {
            const validatedPayload = JSON.parse(result.payload);
            validationResult = validatedPayload;
            validateExpire(validatedPayload.exp);
            validateNonce(validatedPayload.nonce);
            validateIssuer(validatedPayload.iss);
            validateClient(validatedPayload.azp);
        }, function (err) {
            console.log("unable to validate idToken\n" + err);
            validationResult = null;
        });
};
