import React, { useRef, useState } from "react";

import AddBtn from "components/common/elements/AddBtn";
import { useDispatch } from "react-redux";
import { auctionUploadActions } from "store/auctionUpload";
import { OverlayPanel } from "primereact/overlaypanel";
import styles from "components/auction/list/AuctionAddBtn.module.css";

interface AuctionAddBtnProps {
  modalHandler: (visible: boolean) => void;
  visible: boolean;
}

// meme-list page에서 meme-upload로 이동하는 버튼
const AuctionAddBtn: React.FC<AuctionAddBtnProps> = ({
  visible,
  modalHandler,
}) => {
  const op = useRef<any>(null);
  const dispatch = useDispatch();

  return (
    <div className={styles.addBtnContainer}>
      <div
        className={styles.btnContainer}
        onClick={(e) => op.current.toggle(e)}
      >
        <AddBtn />
      </div>

      <OverlayPanel ref={op}>
        <div
          onClick={() => {
            dispatch(
              auctionUploadActions.controlModal({
                visible: true,
                sellerId: JSON.parse(sessionStorage.getItem("user")!).userId,
              })
            );
            modalHandler(true);
          }}
        >
          경매 등록하기
        </div>
      </OverlayPanel>
    </div>
  );
};

export default AuctionAddBtn;
