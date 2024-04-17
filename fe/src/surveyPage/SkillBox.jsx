import React from 'react';
import styled from 'styled-components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDesktop, faCog, faLeaf } from '@fortawesome/free-solid-svg-icons';

const Box = styled.div`
  padding: 10px;
  border-radius: 5px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  cursor: pointer;
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.05);
  }
`;

const SkillBox = ({ iconName, borderColor, text }) => {
  let icon;
  switch (iconName) {
    case 'computer':
      icon = faDesktop;
      break;
    case 'gear':
      icon = faCog;
      break;
    case 'leaf':
      icon = faLeaf;
      break;
    default:
      icon = null;
  }

  return (
    <Box style={{border: `3px solid ${borderColor}`, color: `${borderColor}`}}>
      {icon && <FontAwesomeIcon icon={icon} style={{ fontSize: '24px'}} />}
      <span style={{ color: 'black', fontWeight: 'bold'}}>{text}</span>
    </Box>
  );
}

export default SkillBox;
