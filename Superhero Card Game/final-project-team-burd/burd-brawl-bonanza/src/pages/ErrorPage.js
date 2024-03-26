import { useRouteError } from "react-router-dom";

export default function ErrorPage() {
    
    // Gets the routing error that was thrown
    const error = useRouteError();
    console.error(error);

    return (
        <div id="error-page">
            <h1>Ruh Roh, Raggy!</h1>
            <p>An unexpected error occured with this message:</p>
            <p>
                <i>{error.statusText || error.message}</i>
            </p>
        </div>
    );
}