import jwt from 'jsonwebtoken';

export const PRIVATE_KEY = 'This is a very strong AMS backend API private key.';

export const auth = (req, res, next) => {
  const authorization = req.headers.authorization;

  if (!authorization)
    return res.status(403).send("Authorization header is missing.");

  const [authType, token] = authorization.split(' ');

  if (!(authType === "Bearer" && token))
    return res.status(403).send("Bearer token is missing.");

  try {
    req.auth = jwt.verify(token, PRIVATE_KEY);
    next();
  } catch (error) {
    return res.status(403).send(error.message);
  }
}

export const employeeAuth = (req, res, next) => {
  const authorization = req.headers.authorization;

  if (!authorization)
    return res.status(403).send("Authorization header is missing.");

  const [authType, token] = authorization.split(' ');

  if (!(authType === "Bearer" && token))
    return res.status(403).send("Bearer token is missing.");

  try {
    req.auth = jwt.verify(token, PRIVATE_KEY);

    if (req.auth.is_employee) {
      next();
    } else {
      return res.status(403).send("You must be an employee");
    }
  } catch (error) {
    return res.status(403).send(error.message);
  }
}

export const userAuth = (req, res, next) => {
  const authorization = req.headers.authorization;

  if (!authorization)
    return res.status(403).send("Authorization header is missing.");

  const [authType, token] = authorization.split(' ');

  if (!(authType === "Bearer" && token))
    return res.status(403).send("Bearer token is missing.");

  try {
    req.auth = jwt.verify(token, PRIVATE_KEY);

    if (req.auth.is_employee) {
      return res.status(403).send("You must be a user.");
    } else {
      next();
    }
  } catch (error) {
    return res.status(403).send(error.message);
  }
}
