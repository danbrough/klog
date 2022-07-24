
set_gradle_prop(){
  sed -i gradle.properties  -e 's|^'$1'=.*|'$1'='$2'|g'
}

get_gradle_prop(){
  cat gradle.properties  | sed -n -e '/'$1'=/p' | sed -e 's|'$1'=||g'
}

is_mac() {
  if [ "$MACHTYPE" = "x86_64-apple-darwin21" ]; then
    return 0
  else
    return 1
  fi
}

