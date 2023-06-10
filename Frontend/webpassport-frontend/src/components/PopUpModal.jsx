import CloseIcon from '@mui/icons-material/Close';
import React, { ReactNode, useEffect, useRef } from 'react'
import "../styles/popupmodal.scss"


const PopUpModal = ({
  children,
  className,
  closePopUp,
  disableXbutton = false,
  disableClickOutside = false,
}) => {
  const ref = useRef(null)
  useEffect(() => {
    document.addEventListener('click', handleClickOutside, true)
    return () => {
      document.removeEventListener('click', handleClickOutside, true)
    }
  }, [ref])
  const handleClickOutside = (e) => {
    if (ref.current && !ref.current.contains(e.target)) {
      !disableClickOutside && closePopUp
      console.log('Clicked Outside')
    } else {
      console.log('Clicked Inside')
    }
  }
  return (
    <div className="return-container">
      <div
        className={`return-content`}
        ref={ref}
      >
        {!disableXbutton && (
          <button
            className="close-button"
            onClick={closePopUp}>
            <CloseIcon />
          </button>
        )}
        {children}
      </div>
    </div>
  )
}

export { PopUpModal }