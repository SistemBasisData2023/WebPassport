import "../styles/navbar.scss";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import PostAddIcon from '@mui/icons-material/PostAdd';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import LogoutIcon from '@mui/icons-material/Logout';
import { Link } from "react-router-dom";
import { useContext, useState } from "react";
import { AuthContext } from "../context/authContext";
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";

const Navbar = () => {
  const { currentAccount, logout } = useContext(AuthContext);
  const [visible, setVisible] = useState(false);

  const closeDialog = () => {
    setVisible(false);
  };
  const openDialog = () =>{
    setVisible(true)
  }

  
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
        <span className="logout" onClick={openDialog}><LogoutIcon /></span>
        <Link to={`account/${currentAccount.account_id}/addrequest`} style={{ textDecoration: "none", color: 'white' }}>
          <PostAddIcon />
        </Link>
        <div className="user">
            <Link to= {`account/${currentAccount.account_id}`}>
            <AccountCircleIcon style={{ color: 'white' }}/>
            </Link>
          <span>{currentAccount.email}</span>
        </div>
      </div>
        <Dialog open={visible} onClose={closeDialog}>
          <DialogTitle>{"Logout"}</DialogTitle>
          <DialogContentText>{"Do you want to logout?"}</DialogContentText>
          <DialogActions>
            <button onClick={logout}>Yes</button>
            <button onClick={closeDialog}>No</button>
          </DialogActions>
        </Dialog>
    </div>
  );
};

export default Navbar;