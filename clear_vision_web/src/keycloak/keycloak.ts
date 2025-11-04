import Keycloak from 'keycloak-js';

const keycloakConfig = {
    url: import.meta.env.VITE_KEYCLOAK_URL,
    realm: 'clear_vision',
    clientId: 'clear_vision_web',
};

const keycloak = new Keycloak(keycloakConfig);

export default keycloak;