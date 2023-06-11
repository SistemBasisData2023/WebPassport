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
        <span >WEB PASSPORT</span>
        <Link to={`/admin/${admin.admin_id}`} style={{ textDecoration: "none", color: '#f72d45', width: 0 }}>
            <HomeOutlinedIcon className="icon" />
        </Link>
      </div> : 
      <div className="left">
        <span >WEB PASSPORT</span>
        <Link to={`/account/${currentAccount.account_id}`} style={{ textDecoration: "none", color: '#f72d45', width: 0 }}>
            <HomeOutlinedIcon className="icon" />
        </Link>
      
      </div>}
      {admin != null ? 
      <div className="right">
        <span onClick={openDialog}><LogoutIcon className="icon" /></span>
        <Link to={`/admin/${admin.admin_id}/request`} style={{ textDecoration: "none", color: '#f72d45', width: 0 }}>
          <PostAddIcon className="icon"/>
        </Link>
        <div className="user">
            <Link to={`/admin/${admin.admin_id}/account`}  style={{width: 0}}>
              <AccountCircleIcon className="icon" />
            </Link>
          <span>{admin.email}</span>
        </div>
      </div> : 
      <div className="right">
        <span onClick={openDialog}><LogoutIcon className="icon" /></span>
        <Link to={`account/${currentAccount.account_id}/addrequest`} style={{ textDecoration: "none", color: '#f72d45', width: 0 }}>
          <PostAddIcon className="icon"/>
        </Link>
        <div className="user">
            <Link to= {`account/${currentAccount.account_id}/info`} style={{width: 0}}>
            <AccountCircleIcon className="icon" />
            </Link>
          <span>{currentAccount.email}</span>
      </div>
    </div>}
        <Dialog open={visible} onClose={closeDialog} PaperProps={{ sx: {fontWeight:"bold",  position: "fixed", top: "35%", left: "42%", margin: "0", minWidth: "20vw", padding:"10px 0px" } }}>
          <DialogTitle style={{padding:"10px 0px", display: "flex", alignItems: "center", justifyContent: "center"}}>LOGOUT </DialogTitle>
          <DialogContentText style={{padding:"10px 0px", fontSize:"12px", display: "flex", alignItems: "center", justifyContent: "center"}} >{"Do you want to logout?"}</DialogContentText>
          <DialogActions style={{padding: "10px 0px", gap:"10px", display: "flex", justifyContent: "center"}}>
            <button style={
              {
                minWidth: "60px",
                minHeight: "30px", 
                backgroundColor: "rgb(40, 82, 122)", 
                color: "white", 
                border: "none", 
                borderRadius: "5px",                  
                }} onClick={logout}>Yes</button>
            <button style={
              {
                minWidth: "60px", 
                minHeight: "30px", 
                backgroundColor: "#f72d45", 
                color: "white", border: "none", 
                borderRadius: "5px"}} onClick={closeDialog}>No</button>
          </DialogActions>
        </Dialog>
    </div>
  );
};

export default Navbar;