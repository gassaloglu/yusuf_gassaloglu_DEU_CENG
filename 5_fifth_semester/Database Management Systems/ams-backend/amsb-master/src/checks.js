export function emailCheck(input)
{
	const EMAIL_REGEX =
	    /^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])$/;
	return EMAIL_REGEX.test(input);
}

export function phoneCheck(input)
{
	const PHONE_REGEX = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/;
	return PHONE_REGEX.test(input);
}

export function alphabeticCheck(input)
{
	const ALPHA_REGEX = /^[\p{L}-]+$/ug;
	return ALPHA_REGEX.test(input);
}

export function numericCheck(input)
{
	const NUM_REGEX = /'\d+$/;
	return NUM_REGEX.test(input);
}

export const checkProps = (obj, props) => {
    if (!obj) return false;

    return Object.entries(props)
        .every(([field, type]) => typeof(obj[field]) === type);
}

export const checkAuth = (obj, authList) => {
	if (!obj) return false;
	return authList.includes(obj);
}
