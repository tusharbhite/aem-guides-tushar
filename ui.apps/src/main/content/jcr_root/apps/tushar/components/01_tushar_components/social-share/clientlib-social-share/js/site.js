const input = document.getElementById("text");
const copyButton = document.getElementById("copy");

const copyText = (e) => {
  // window.getSelection().selectAllChildren(textElement);
  input.select(); //select input value
  document.execCommand("copy");
  e.currentTarget.setAttribute("tooltip", "Copied!");
};

const resetTooltip = (e) => {
  e.currentTarget.setAttribute("tooltip", "Copy to clipboard");
};

copyButton.addEventListener("click", (e) => copyText(e));
copyButton.addEventListener("mouseover", (e) => resetTooltip(e));
