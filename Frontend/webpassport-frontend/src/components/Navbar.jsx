import "../styles/navbar.scss";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import PostAddIcon from '@mui/icons-material/PostAdd';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import LogoutIcon from '@mui/icons-material/Logout';
import { Link } from "react-router-dom";
import { useContext } from "react";
import { AuthContext } from "../context/authContext";

const Navbar = () => {

   const { currentAccount, logout } = useContext(AuthContext);
  
    return (
    <div className="navbar">
      <div className="left">
        <span>WebPassport</span>
        <Link to="/" style={{ textDecoration: "none", color: 'white' }}>
            <HomeOutlinedIcon />
        </Link>
        <NotificationsOutlinedIcon />
        
        <div className="search">
          <SearchOutlinedIcon />
          <input type="text" placeholder="Search..." />
        </div>
      </div>
      <div className="right">
        <span className="logout" onClick={logout}><LogoutIcon /></span>
        <Link to="/addrequest" style={{ textDecoration: "none", color: 'white' }}>
          <PostAddIcon />
        </Link>
        <div className="user">
            <Link to= {`account/${currentAccount.account_id}`}>
            <AccountCircleIcon style={{ color: 'white' }}/>
            </Link>
          <span>{currentAccount.email}</span>
        </div>
      </div>
    </div>
  );
};

export default Navbar;