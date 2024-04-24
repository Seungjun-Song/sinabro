import { motion } from "framer-motion";
import { useState } from "react";
const WhatPos = ({ item }) => {
  const getColor = (item) => {
    // 여기에 item에 따라 적절한 색상을 반환하는 조건을 추가하세요
    // 예를 들어, item이 "A"일 때는 빨간색, "B"일 때는 파란색 등등...
    return item === "FE"
      ? "#3DC7AE"
      : item === "BE"
      ? "#315DCC"
      : item === "FULL"
      ? "#6C31CC"
      : "black";
  };
  const getJob = (item) => {
    // 여기에 item에 따라 적절한 색상을 반환하는 조건을 추가하세요
    // 예를 들어, item이 "A"일 때는 빨간색, "B"일 때는 파란색 등등...
    return item === "FE"
      ? "프론트엔드"
      : item === "BE"
      ? "백엔드"
      : item === "FULL"
      ? "풀스택"
      : "역할 선택";
  };
  let imgsrc = "";
  if (item == "FE") {
    imgsrc = "/images/FEIcon.png";
  } else if (item == "BE") {
    imgsrc = "/images/BEIcon.png";
  } else if (item == "FULL") {
    imgsrc = "/images/FULLIcon.png";
  }
  return (
    <>
      <div
        className="shadow d-flex gap-3"
        style={{
          padding: "0.7rem",
          borderRadius: "0.5rem",
          border: "3px solid",
          width: "12rem",
          justifyContent: "center",
          alignItems: "center",
          borderColor: getColor(item),
          // height:"100%"
          height:"4rem"
        }}
      >
        <img style={{width:"1.3rem"}} src={imgsrc} />
        <div style={{ textAlign: "center", fontSize: "1.1rem" }}>
          {getJob(item)}
        </div>
      </div>
    </>
  );
};

export default WhatPos;