import "../styles/navbar.scss";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
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
  const { currentAccount, admin, logout } = useContext(AuthContext);
  const [ visible, setVisible ] = useState(false);

  const closeDialog = () => {
    setVisible(false);
  };
  const openDialog = () =>{
    setVisible(true)
  }

  
    return (
    <div className="navbar">
      {admin != null ? 
      <div className="left"> 
        <span>WebPassport</span>
        <Link to="/admin" style={{ textDecoration: "none", color: 'white' }}>
            <HomeOutlinedIcon className="icon" />
        </Link>
        <NotificationsOutlinedIcon className="icon"/>
        <div className="search">
          <SearchOutlinedIcon />
          <input type="text" placeholder="Search..." />
        </div>
      </div> : 
      <div className="left">
        <span>WebPassport</span>
        <Link to="/" style={{ textDecoration: "none", color: 'white' }}>
            <HomeOutlinedIcon className="icon" />
        </Link>
        <NotificationsOutlinedIcon className="icon"/>
        <div className="search">
          <SearchOutlinedIcon />
          <input type="text" placeholder="Search..." />
        </div>
      </div>}
      {admin != null ? 
      <div className="right">
        <span onClick={openDialog}><LogoutIcon className="icon" /></span>
        <Link style={{ textDecoration: "none", color: 'white' }}>
          <PostAddIcon className="icon"/>
        </Link>
        <div className="user">
            <Link>
              <AccountCircleIcon className="icon" style={{ color: 'white' }}/>
            </Link>
          <span>{admin.email}</span>
        </div>
      </div> : 
      <div className="right">
        <span onClick={openDialog}><LogoutIcon className="icon" /></span>
        <Link to={`account/${currentAccount.account_id}/addrequest`} style={{ textDecoration: "none", color: 'white' }}>
          <PostAddIcon className="icon"/>
        </Link>
        <div className="user">
            <Link to= {`account/${currentAccount.account_id}`}>
            <AccountCircleIcon className="icon" style={{ color: 'white' }}/>
            </Link>
          <span>{currentAccount.email}</span>
      </div>
    </div>}
        <Dialog open={visible} onClose={closeDialog} PaperProps={{ sx: {fontWeight:"bold",  position: "fixed", top: "35%", left: "42%", margin: "0", minWidth: "20vw", padding:"10px 0px" } }}>
          <DialogTitle style={{padding:"10px 0px", display: "flex", alignItems: "center", justifyContent: "center"}}>LOGOUT </DialogTitle>
          <DialogContentText style={{padding:"10px 0px", fontSize:"12px", display: "flex", alignItems: "center", justifyContent: "center"}} >{"Do you want to logout?"}</DialogContentText>
          <DialogActions style={{padding: "10px 0px", gap:"10px", display: "flex", justifyContent: "center"}}>
            <button style={{minWidth: "60px", minHeight: "30px", backgroundColor: "rgb(40, 82, 122)", color: "white", border: "none", borderRadius: "5px"}} onClick={logout}>Yes</button>
            <button style={{minWidth: "60px", minHeight: "30px", backgroundColor: "rgb(40, 82, 122)", color: "white", border: "none", borderRadius: "5px"}} onClick={closeDialog}>No</button>
          </DialogActions>
        </Dialog>
    </div>
  );
};

export default Navbar;