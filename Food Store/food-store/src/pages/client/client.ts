import { checkAuth } from "../../utils/auth";


// Guard — lo primero
checkAuth("client");

window.location.href = "/src/pages/store/home/home.html";

